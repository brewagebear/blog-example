package simple_chatting_server;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.net.ServerSocket;
import java.nio.ByteBuffer;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.ServerSocketChannel;
import java.nio.channels.SocketChannel;
import java.util.Iterator;
import java.util.Vector;
import java.util.logging.FileHandler;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class Server {

    private static final String HOST = "localhost";
    private static final int PORT = 9090;

    private static FileHandler fileHandler;
    private static final Logger log = LogManager.getLogger(Server.class);

    private Selector selector = null;
    private ServerSocketChannel serverSocketChannel = null;
    private ServerSocket serverSocket = null;

    private Vector<SocketChannel> room = new Vector();

    public void init() {
        try {
            // 셀렉터를 연다.
            selector = Selector.open();
            // 서버소켓채널을 생성한다.
            serverSocketChannel = ServerSocketChannel.open();
            // 비블록킹 모드로 설정한다.
            serverSocketChannel.configureBlocking(false);
            // 서버소켓채널과 연결된 소켓을 가져온다.
            serverSocket = serverSocketChannel.socket();
            // 주어진 호스트와 포트로 소켓을 바인딩한다.
            InetSocketAddress isa = new InetSocketAddress(HOST, PORT);
            serverSocket.bind(isa);

            //서버소켓채널을 셀렉터에 등록한다.
            serverSocketChannel.register(selector, SelectionKey.OP_ACCEPT);
        } catch (IOException e) {
            log.warn("Server.init()", e);
        }
    }

    public void start() {
        log.info("Server is started....");
        try {
            while (this.serverSocketChannel.isOpen()) {
                log.info("요청을 기다리는 중..");
                // 셀렉터의 select() 메서드로 준비된 이벤트가 존재하는지 확인
                selector.select();
                // 준비된 이벤트들을 하나씩 처리한다.
                Iterator<SelectionKey> iterator = selector.selectedKeys().iterator();

                while (iterator.hasNext()) {
                    SelectionKey key = iterator.next();
                    iterator.remove();

                    if(key.isAcceptable()) accept(key);
                    if(key.isReadable()) read(key);
                }
            }
        } catch (Exception e) {
            log.warn("Server start()", e);
        }
    }

    private void accept(SelectionKey key) {
        ServerSocketChannel server = (ServerSocketChannel) key.channel();
        SocketChannel sc;
        try {
            // 서버소켓채널의 accept() 메서드로 서버소켓을 생성한다.
            sc = server.accept();

            registerChannel(selector, sc, SelectionKey.OP_READ);
            System.out.println(sc.toString() + " 클라이언트가 접속했습니다.");

        } catch (IOException e) {
            log.warn("Server.accept()", e);
        }
    }

    private void registerChannel(Selector selector, SocketChannel sc, int opCode)
        throws IOException {
        if (sc == null) {
            log.info("Invalid Connection");
            return;
        }
        sc.configureBlocking(false);
        sc.register(selector, opCode);
        addUser(sc);
    }

    private void read(SelectionKey key) {
        // SelectionKey로부터 소켓채널을 얻어온다.
        SocketChannel sc = (SocketChannel) key.channel();

        // ByteBuffer를 생성한다.
        ByteBuffer buffer = ByteBuffer.allocateDirect(1024);

        try {
            int read = sc.read(buffer);
            if(read == -1) {
                sc.socket().close();
                sc.close();
                removeUser(sc);
                System.out.println(sc.toString() + " 클라이언트가 접속을 해제하였습니다.");
            }
        } catch (IOException e) {
            try {
                sc.close();
            } catch (IOException ex) {
            }
            removeUser(sc);
            System.out.println(sc.toString() + " 클라이언트가 접속을 해제하였습니다.");
        }

        try {
            broadcast(buffer);
        } catch (IOException e) {
            log.warn("Server.broadcast()", e);
        }

        clearBuffer(buffer);
    }

    private void broadcast(ByteBuffer buffer) throws IOException {
        buffer.flip();

        for (SocketChannel sc : room) {
            if (sc != null) {
                sc.write(buffer);
                buffer.rewind();
            }
        }
    }

    private void clearBuffer(ByteBuffer buffer) {
        if (buffer != null) {
            buffer.clear();
            buffer = null;
        }
    }

    private void addUser(SocketChannel sc) {
        room.add(sc);
    }

    private void removeUser(SocketChannel sc) {
        room.remove(sc);
    }

    public static void main(String[] args) {
        Server server = new Server();
        server.init();
        server.start();
    }
}

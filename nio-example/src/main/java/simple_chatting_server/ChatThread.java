package simple_chatting_server;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.nio.ByteBuffer;
import java.nio.channels.SocketChannel;
import java.nio.charset.StandardCharsets;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class ChatThread extends Thread {

    private static final Logger log = LogManager.getLogger(ChatThread.class);

    private SocketChannel sc = null;

    public ChatThread(SocketChannel sc) {
        this.sc = sc;
    }

    @Override
    public void run() {
        ByteBuffer buffer = ByteBuffer.allocateDirect(1024);
        try {
            while (!Thread.currentThread().isInterrupted()) {
                buffer.clear();
                BufferedReader bufferedReader = new BufferedReader(
                    new InputStreamReader(System.in));

                String message = bufferedReader.readLine();

                if (message.equals("quit") || message.equals("shutdown")) {
                    sc.socket().close();
                    sc.close();
                    System.exit(0);
                }

                buffer.put(message.getBytes(StandardCharsets.UTF_8));
                buffer.flip();

                sc.write(buffer);
            }
        } catch (Exception e) {
            log.warn("run()", e);
        } finally {
            clearBuffer(buffer);
        }
    }

    private void clearBuffer(ByteBuffer buffer) {
        if (buffer != null) {
            buffer.clear();
            buffer = null;
        }
    }
}

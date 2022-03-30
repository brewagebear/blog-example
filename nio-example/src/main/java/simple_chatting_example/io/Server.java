package simple_chatting_example.io;

import java.net.ServerSocket;
import java.net.Socket;
import java.util.concurrent.ConcurrentHashMap;

public class Server {

    public static void main(String[] args) {
        try {
            ServerSocket server = new ServerSocket(10001);
            System.out.println("접속을 기다립니다.");

            ConcurrentHashMap<Object, Object> hashMap = new ConcurrentHashMap<>();

            while(true) {
                Socket sock = server.accept();
                ChatThread chatThread = new ChatThread(sock, hashMap);
                chatThread.start();
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}

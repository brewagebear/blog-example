package simple_chatting_example.io;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class ChatThread extends Thread {

    private Socket socket;
    private String id;
    private BufferedReader reader;
    private ConcurrentHashMap<Object, Object> map;
    private boolean initFlag = false;

    public ChatThread(Socket socket, ConcurrentHashMap<Object, Object> map) {
        this.socket = socket;
        this.map = map;

        try {
            PrintWriter printWriter = new PrintWriter(
                new OutputStreamWriter(socket.getOutputStream()));

            reader = new BufferedReader(
                new InputStreamReader(socket.getInputStream()));

            id = reader.readLine();

            broadcast(id + "님이 접속했습니다.");

            System.out.println("접속한 사용자의 아이디는 " + id + " 입니다. ");

            map.put(this.id, printWriter);

            this.initFlag = true;

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void run() {
        try {
            String line = null;
            while ((line = reader.readLine()) != null) {
                if (line.equals("/quit")) {
                    break;
                }
                if (line.indexOf("/to ") == 0) {
                    sendMessage(line);
                } else {
                    broadcast(id + " : " + line);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            map.remove(id);
            broadcast(id + "님이 접속 종료했습니다.");
            try {
                if (socket != null) {
                    socket.close();
                }
            } catch (Exception ignored) {
            }
        }
    }

    private void sendMessage(String message) {
        int start = message.indexOf(" ") + 1;
        int end = message.indexOf(" ", start);

        if (end != -1) {
            String to = message.substring(start, end);
            String message2 = message.substring(end + 1);
            Object obj = map.get(to);
            if(obj != null) {
                PrintWriter printWriter = (PrintWriter) obj;
                printWriter.println(id + " 님이 다음의 귓속말을 보내셨습니다. : " + message2);
                printWriter.flush();
            }
        }
    }

    private void broadcast(String message) {
        Collection<Object> values = map.values();

        for (Object value : values) {
            PrintWriter printWriter = (PrintWriter) value;
            printWriter.println(message);
            printWriter.flush();
        }
    }
}

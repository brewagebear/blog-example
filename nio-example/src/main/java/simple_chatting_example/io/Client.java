package simple_chatting_example.io;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.net.Socket;

public class Client {

    public static void main(String[] args) {

        boolean endFlag = false;

        try (Socket socket = new Socket(args[1], 10001);
             PrintWriter writer = new PrintWriter(new OutputStreamWriter(socket.getOutputStream()));
             BufferedReader reader = new BufferedReader(new InputStreamReader(socket.getInputStream()));
             BufferedReader keyboard = new BufferedReader(new InputStreamReader(System.in))){

            writer.println(args[0]);
            writer.flush();

            InputThread inputThread = new InputThread(socket, reader);
            inputThread.start();

            String line = null;

            while ((line = keyboard.readLine()) != null) {
                writer.println(line);
                writer.flush();
                if (line.equals("/quit")) {
                    endFlag = true;
                    break;
                }
            }
            System.out.println("클라이언트의 접속을 종료합니다.");

        } catch (Exception e) {
            if(!endFlag) {
                System.out.println(e);
            }
        }
    }
}

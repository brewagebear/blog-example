package simple_chatting_example.io;

import java.io.BufferedReader;
import java.net.Socket;

public class InputThread extends Thread {

    private Socket socket = null;
    private BufferedReader reader = null;

    public InputThread(Socket socket, BufferedReader reader) {
        this.socket = socket;
        this.reader = reader;
    }

    public void run() {
        try {
            String line = null;
            while ((line = reader.readLine()) != null) {
                System.out.println(line);
            }
        } catch (Exception ignored) {
        } finally {
            try {
                if (reader != null) {
                    reader.close();
                }
            } catch (Exception ignored) {
            }
            try {
                if (socket != null) {
                    socket.close();
                }
            } catch (Exception ignored) {
            }
        }
    }
}

package nio_copy;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;

public class FlieSizeBufferIO extends MyTimer {

    private static final String DEST_PATH = "/Users/liquid.bear/Downloads/io_test_out.txt";

    public static void main(String[] args) throws IOException {
        start();
        copy();
        end("Buffer has same size File I/O");
    }

    public static void copy() throws IOException {
        try (FileInputStream fileInputStream = new FileInputStream(MyTimer.PATH);
            FileOutputStream fileOutputStream = new FileOutputStream(DEST_PATH)) {

            // 500MB 파일 크기 만큼 버퍼를 만든다.
            byte[] buffer = new byte[fileInputStream.available()];

            fileInputStream.read(buffer);
            fileOutputStream.write(buffer);
        }
    }
}

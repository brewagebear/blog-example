package nio_copy;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;

public class NoneDirectByteBufferIO extends MyTimer {

    private static final String DEST_PATH = "/Users/liquid.bear/Downloads/io_test_out.txt";

    public static void main(String[] args) throws IOException {
        start();
        copy();
        end("None-Direct ByteBuffer I/O");
    }

    public static void copy() throws IOException {
        try (FileInputStream fileInputStream = new FileInputStream(MyTimer.PATH);
            FileOutputStream fileOutputStream = new FileOutputStream(DEST_PATH);
            FileChannel fileInputChannel = fileInputStream.getChannel();
            FileChannel fileOutputChannel = fileOutputStream.getChannel()) {

            ByteBuffer byteBuffer = ByteBuffer.allocate((int) fileInputChannel.size());
            fileInputChannel.read(byteBuffer);
            byteBuffer.flip();
            fileOutputChannel.write(byteBuffer);
        }
    }
}

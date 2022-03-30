import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.nio.channels.FileChannel;
import java.nio.channels.FileLock;

public class FileLocking {

    public static void main(String[] args) {
        File file = new File("/Users/liquid.bear/Downloads/test.txt");

        try (FileChannel channel = new RandomAccessFile(file, "rw").getChannel()){
            try (FileLock lock = channel.lock(0, Long.MAX_VALUE, true)) {
                boolean isShared = lock.isShared();
                System.out.println("Is Shared Lock? : " + isShared);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}

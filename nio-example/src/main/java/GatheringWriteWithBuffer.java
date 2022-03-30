import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;
import java.nio.channels.GatheringByteChannel;
import java.nio.charset.StandardCharsets;

public class GatheringWriteWithBuffer {

    public static void main(String[] args) throws IOException {
        FileOutputStream fileOutputStream = new FileOutputStream(
            "/Users/liquid.bear/Downloads/test-gathering.txt");

        GatheringByteChannel channel = fileOutputStream.getChannel();

        ByteBuffer header = ByteBuffer.allocateDirect(20);
        ByteBuffer body = ByteBuffer.allocateDirect(40);
        ByteBuffer[] buffers = { header, body };

        header.put("Hello ".getBytes(StandardCharsets.UTF_8));
        body.put("World!".getBytes(StandardCharsets.UTF_8));

        header.flip();
        body.flip();

        channel.write(buffers);
        channel.close();
    }
}

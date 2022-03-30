import java.io.FileInputStream;
import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.channels.ScatteringByteChannel;

public class ScatterBuffer {

    public static void main(String[] args) throws IOException {
        FileInputStream fileInputStream = new FileInputStream(
            "/Users/liquid.bear/Downloads/test-scattering.txt");

        ScatteringByteChannel channel = fileInputStream.getChannel();

        ByteBuffer header = ByteBuffer.allocateDirect(100);
        ByteBuffer body = ByteBuffer.allocateDirect(200);

        ByteBuffer[] buffers = { header, body };

        int readCount = (int) channel.read(buffers);
        channel.close();

        System.out.println("Read Count : " + readCount);
        System.out.println("\n//------------------------------//\n");

        header.flip();
        body.flip();

        byte[] headerData = new byte[100];
        header.get(headerData);

        System.out.println("Header : " + new String(headerData));

        System.out.println("\n//------------------------------//\n");

        byte[] bodyData = new byte[152];
        body.get(bodyData);
        System.out.println("Body : " + new String(bodyData));
    }
}

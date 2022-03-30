import java.nio.ByteBuffer;

public class BulkWriteBuffer {

    public static void main(String[] args) {
        ByteBuffer buffer = ByteBuffer.allocate(10);
        buffer.position(5);
        buffer.mark();

        System.out.println("Pos : " + buffer.position() + ", Limit : " + buffer.limit());

        byte[] bytes = new byte[15];

        for (int i = 0; i < bytes.length; i++) {
            bytes[i] = (byte) i;
        }

        int size = buffer.remaining();
        if (bytes.length < size) {
            size = bytes.length;
        }

        buffer.put(bytes, 0, size);
        System.out.println("Pos : " + buffer.position() + ", Limit : " + buffer.limit());

        buffer.reset();
        doSomething(buffer, size);

    }

    private static void doSomething(ByteBuffer buffer, int size) {
        for (int i = 0; i < size; i++) {
            System.out.println("byte - " + buffer.get());
        }
    }
}

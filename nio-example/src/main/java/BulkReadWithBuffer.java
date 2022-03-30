import java.nio.ByteBuffer;

public class BulkReadWithBuffer {

    public static void main(String[] args) {
        ByteBuffer buffer = ByteBuffer.allocate(10);

        buffer.put((byte) 0)
            .put((byte) 1)
            .put((byte) 2)
            .put((byte) 3)
            .put((byte) 4);

        buffer.mark();

        buffer.put((byte) 5)
            .put((byte) 6)
            .put((byte) 7)
            .put((byte) 8)
            .put((byte) 9);

        buffer.reset();

        byte[] bytes = new byte[15];

        int size = buffer.remaining();
        if (bytes.length < size) {
            size = bytes.length;
        }

        buffer.get(bytes, 0, size);
        System.out.println("Pos : " + buffer.position() + ", Limit : " + buffer.limit());

        doSomething(bytes, size);
    }

    private static void doSomething(byte[] bytes, int size) {
        for (int i = 0; i < size; i++) {
            System.out.println("byte - " + bytes[i]);
        }
    }
}

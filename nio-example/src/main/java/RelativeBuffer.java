import java.nio.ByteBuffer;

public class RelativeBuffer {

    public static void main(String[] args) {
        ByteBuffer buffer = ByteBuffer.allocate(10);
        System.out.println("Init Pos : " + buffer.position());
        System.out.println("Init Limit : " + buffer.limit());
        System.out.println("Init Capacity : " + buffer.capacity());

        buffer.mark();

        buffer.put((byte) 10)
            .put((byte) 11)
            .put((byte) 12);

        buffer.reset();

        System.out.println("Value : " + buffer.get() + ", Pos : " + buffer.position());
        System.out.println("Value : " + buffer.get() + ", Pos : " + buffer.position());
        System.out.println("Value : " + buffer.get() + ", Pos : " + buffer.position());
        System.out.println("Value : " + buffer.get() + ", Pos : " + buffer.position());
    }
}

package nio_copy;

public class MyTimer {

    protected static final String PATH = "/Users/liquid.bear/Downloads/nio_test.txt";

    private static long startAt;
    private static long endAt;

    protected static void start() {
        startAt = System.currentTimeMillis();
    }

    protected static void end(String name) {
        endAt = System.currentTimeMillis();
        System.out.println("[ " + name + " Time : " + (endAt - startAt) + " ]");
    }
}

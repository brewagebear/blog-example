import java.io.*;

public class NotUsedBuffer {

    private static final String COPY_ORIGIN_FILE = "/Users/liquid.bear/Downloads/test.txt";

    private static final String COPY_DEST_FILE = "/Users/liquid.bear/Downloads/test2.txt";

    public static void main(String[] args) {
        try {
            long startTime = System.currentTimeMillis();
            copy(COPY_ORIGIN_FILE, COPY_DEST_FILE);
            long endTime = System.currentTimeMillis();

            System.out.println("버퍼를 사용하지 않을 경우 처리 시간 : " + (endTime - startTime) + " milli seconds");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private static void copy(String origin, String dest) throws IOException {
        try(InputStream in = new FileInputStream(origin);
            OutputStream out = new FileOutputStream(dest)) {

            while(true) {
                int byteData = in.read();

                if(byteData == -1) {
                    break;
                }
                out.write(byteData);
            }
        }
    }
}

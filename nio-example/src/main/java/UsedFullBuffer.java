import java.io.*;

public class UsedFullBuffer {
    private static final String COPY_ORIGIN_FILE = "/Users/liquid.bear/Downloads/test.txt";

    private static final String COPY_DEST_FILE = "/Users/liquid.bear/Downloads/test2.txt";

    public static void main(String[] args) {
        try {
            long startTime = System.currentTimeMillis();
            copy(COPY_ORIGIN_FILE, COPY_DEST_FILE);
            long endTime = System.currentTimeMillis();

            System.out.println("파일 크기만큼 버퍼를 만들었을 때 : " + (endTime - startTime) + " milli seconds");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private static void copy(String origin, String dest) throws IOException {
        try(InputStream in = new FileInputStream(origin);
            OutputStream out = new FileOutputStream(dest)) {

            int available = in.available();

            byte[] bufferSize = new byte[available];

            while(true) {
                int byteData = in.read(bufferSize);

                if(byteData == -1) {
                    break;
                }
                out.write(byteData);
            }
        }
    }
}

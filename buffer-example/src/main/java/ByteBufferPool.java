import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel.MapMode;
import java.util.ArrayList;
import java.util.List;

public class ByteBufferPool {

    private static final int MEMORY_BLOCK_SIZE = 1024;
    private static final int FILE_BLOCK_SIZE = 2048;

    private final List<ByteBuffer> memoryQueue = new ArrayList();
    private final List<ByteBuffer> fileQueue = new ArrayList();

    private boolean isWait = false;

    public ByteBufferPool(int memorySize, int fileSize, File file) throws IOException {
        if (memorySize > 0) {
            initMemoryBuffer(memorySize);
        }

        if (fileSize > 0) {
            initFileBuffer(fileSize, file);
        }
    }

    private void initMemoryBuffer(int size) {
        int bufferCount = size / MEMORY_BLOCK_SIZE;
        size = bufferCount * MEMORY_BLOCK_SIZE;
        ByteBuffer directMemoryBuffer = ByteBuffer.allocateDirect(size);
        divideBuffer(directMemoryBuffer, MEMORY_BLOCK_SIZE, memoryQueue);
    }

    private void initFileBuffer(int size, File file) throws FileNotFoundException {
        int bufferCount = size / FILE_BLOCK_SIZE;
        size = bufferCount * FILE_BLOCK_SIZE;

        try (RandomAccessFile fileData = new RandomAccessFile(file, "rw")) {
            fileData.setLength(size);
            ByteBuffer fileBuffer = fileData.getChannel()
                .map(MapMode.READ_WRITE, 0L, size);

            divideBuffer(fileBuffer, FILE_BLOCK_SIZE, fileQueue);

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void divideBuffer(ByteBuffer buffer, int blockSize,
        List<ByteBuffer> queue) {

        int bufferCount = buffer.capacity() / blockSize;
        int position = 0;

        for (int i = 0; i < bufferCount; i++) {
            int max = position + blockSize;
            buffer.limit(max);
            queue.add(buffer.slice());
            position = max;
            buffer.position(position);
        }
    }

    public ByteBuffer getMemoryQueue() {
        return getBuffer(memoryQueue, fileQueue);
    }

    public ByteBuffer getFileQueue() {
        return getBuffer(fileQueue, memoryQueue);
    }

    private ByteBuffer getBuffer(List<ByteBuffer> firstQueue, List<ByteBuffer> secondQueue) {
        ByteBuffer buffer = getBuffer(firstQueue, false);
        if (buffer == null) {
            buffer = getBuffer(secondQueue, false);
            if(buffer == null) {
                if(isWait) {
                    buffer = getBuffer(firstQueue, true);
                } else {
                    buffer = ByteBuffer.allocate(MEMORY_BLOCK_SIZE);
                }
            }
        }
        return buffer;
    }

    private ByteBuffer getBuffer(List<ByteBuffer> queue, boolean wait) {
        synchronized (queue) {
            if (queue.isEmpty()) {
                if (wait) {
                    try {
                        queue.wait();
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                } else {
                    return null;
                }
            }
            return queue.remove(0);
        }
    }

    private void putBuffer(ByteBuffer buffer) {
        if (buffer.isDirect()) {
            switch (buffer.capacity()) {
                case MEMORY_BLOCK_SIZE:
                    putBuffer(buffer, memoryQueue);
                    break;
                case FILE_BLOCK_SIZE:
                    putBuffer(buffer, fileQueue);
                    break;
            }
        }
    }

    private void putBuffer(ByteBuffer buffer, List<ByteBuffer> queue) {
        buffer.clear();
        synchronized (queue) {
            queue.add(buffer);
            queue.notify();
        }
    }

    public synchronized void setWait(boolean wait) {
        isWait = wait;
    }
    public synchronized boolean isWait() {
        return isWait;
    }
}

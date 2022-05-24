package selector_example;

import java.io.IOException;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.ServerSocketChannel;
import java.nio.channels.SocketChannel;
import java.util.Set;

public class MultipleSelector {

    public static void main(String[] args) throws IOException {
        Selector selector = Selector.open();

        SocketChannel channel1 = SocketChannel.open();
        channel1.configureBlocking(false);
        SocketChannel channel2 = SocketChannel.open();
        channel2.configureBlocking(false);
        ServerSocketChannel server = ServerSocketChannel.open();
        server.configureBlocking(false);

        server.register(selector, SelectionKey.OP_ACCEPT);
        channel1.register(selector, SelectionKey.OP_READ);
        channel2.register(selector, SelectionKey.OP_READ | SelectionKey.OP_WRITE);

        Set<SelectionKey> keys = selector.keys();

        for (SelectionKey key : keys) {
            System.out.println(key.channel().getClass() + " " + key.interestOps());
        }
    }

}

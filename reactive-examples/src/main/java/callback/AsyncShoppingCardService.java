package callback;

import common.response.Input;
import common.response.Output;

import java.util.function.Consumer;

public class AsyncShoppingCardService implements ShoppingCardService {
    @Override
    public void calculate(Input value, Consumer<Output> consumer) {
        new Thread(() -> {
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

            consumer.accept(new Output());
        }).start();
    }
}

package callback;

import common.response.Input;
import common.response.Output;

import java.util.function.Consumer;

public class SyncShoppingCardService implements ShoppingCardService {
    @Override
    public void calculate(Input value, Consumer<Output> consumer) {
        consumer.accept(new Output());
    }
}

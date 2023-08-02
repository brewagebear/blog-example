package callback;

import common.response.Input;
import common.response.Output;

import java.util.function.Consumer;

public interface ShoppingCardService {
    void calculate(Input value, Consumer<Output> consumer);
}

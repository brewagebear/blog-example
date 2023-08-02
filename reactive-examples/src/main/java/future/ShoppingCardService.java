package future;

import common.response.Input;
import common.response.Output;

import java.util.concurrent.Future;

public interface ShoppingCardService {
    Future<Output> calculate(Input value);
}

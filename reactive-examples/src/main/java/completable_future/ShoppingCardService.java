package completable_future;

import common.response.Input;
import common.response.Output;

import java.util.concurrent.CompletableFuture;

public interface ShoppingCardService {
    CompletableFuture<Output> calculate(Input input);
}

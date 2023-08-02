package completable_future;

import common.response.Input;
import common.response.Output;

import java.util.concurrent.CompletableFuture;

public class CompletableFutureShoppingCardService implements ShoppingCardService{
    @Override
    public CompletableFuture<Output> calculate(Input input) {
        return CompletableFuture.supplyAsync(() -> {
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

            return new Output();
        });
    }
}

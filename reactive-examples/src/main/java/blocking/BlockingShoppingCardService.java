package blocking;

import common.response.Input;
import common.response.Output;

public class BlockingShoppingCardService implements ShoppingCardService {
    @Override
    public Output calculate(Input value) {
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        return new Output();
    }
}

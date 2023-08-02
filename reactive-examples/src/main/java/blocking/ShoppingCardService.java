package blocking;

import common.response.Input;
import common.response.Output;

import java.util.function.Consumer;

public interface ShoppingCardService {
    Output calculate(Input value);
}

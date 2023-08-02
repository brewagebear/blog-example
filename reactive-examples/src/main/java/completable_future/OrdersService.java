package completable_future;

import common.response.Input;

public class OrdersService {
    private final ShoppingCardService shoppingCardService;

    public OrdersService(ShoppingCardService shoppingCardService) {
        this.shoppingCardService = shoppingCardService;
    }

    void process() {
        Input input = new Input();

        shoppingCardService.calculate(input)
                .thenAccept(v -> System.out.println(shoppingCardService.getClass().getSimpleName() + " execution completed"));

        System.out.println(shoppingCardService.getClass().getSimpleName() + " calculate called");
    }

    public static void main(String[] args) throws InterruptedException {
        long start = System.currentTimeMillis();

        OrdersService ordersService = new OrdersService(new CompletableFutureShoppingCardService());

        ordersService.process();
        ordersService.process();

        System.out.println("Total elapsed time in millis is : " + (System.currentTimeMillis() - start));

        Thread.sleep(1000);
    }
}

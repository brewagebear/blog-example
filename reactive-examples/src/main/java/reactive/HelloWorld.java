package reactive;

import lombok.extern.slf4j.Slf4j;
import reactor.core.publisher.Flux;

@Slf4j
public class HelloWorld {
    public static void main(String[] args) throws InterruptedException {
        Flux<String> flux = Flux.just("Hello", "World")
                .map(String::toUpperCase)
                .log();

        flux.subscribe(log::info,
                        error -> log.error(error.getMessage()),
                        () -> log.info("complete")
        );

        Thread.sleep(1000L);
    }



}

package reactive.backpressure;

import lombok.extern.slf4j.Slf4j;
import reactor.core.publisher.Flux;
import reactor.core.scheduler.Schedulers;

import java.time.Duration;


@Slf4j
public class BackPressureStrategyEx1 {

    public static void main(String[] args) throws InterruptedException {
        Flux
                .interval(Duration.ofMillis(1L))
                .onBackpressureError()
                .doOnNext(data -> log.info("# doOnNext: {}", data))
                .publishOn(Schedulers.parallel())
                .subscribe(data -> {
                            try {
                                Thread.sleep(5L);
                            } catch (InterruptedException e) {}
                            log.info("# onNext: {}", data);
                        },
                        error -> log.error("# onError", error));

        Thread.sleep(2000L);
    }
}

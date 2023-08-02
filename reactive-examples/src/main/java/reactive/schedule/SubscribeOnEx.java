package reactive.schedule;

import lombok.extern.slf4j.Slf4j;
import reactor.core.publisher.Flux;
import reactor.core.scheduler.Schedulers;

@Slf4j
public class SubscribeOnEx {
    public static void main(String[] args) throws InterruptedException {
        Flux.fromArray(new Integer[]{1, 2, 3})
                .subscribeOn(Schedulers.boundedElastic())
                .doOnNext(data -> log.info("# doOnNext {}", data))
                .doOnSubscribe(subscription -> log.info("# doOnSubscribe"))
                .subscribe(data -> log.info("# onNext {}", data));

        Thread.sleep(100L);
    }
}

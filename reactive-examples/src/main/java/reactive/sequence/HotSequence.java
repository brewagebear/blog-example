package reactive.sequence;

import lombok.extern.slf4j.Slf4j;
import reactor.core.publisher.Flux;

import java.time.Duration;

@Slf4j
public class HotSequence {
    public static void main(String[] args) throws InterruptedException {
        Flux<String> hotSequence = Flux.just("a", "b", "c", "d", "e")
                .map(String::toUpperCase)
                .delayElements(Duration.ofSeconds(1))
                .publish()
                .refCount(1);

        hotSequence.subscribe(data -> log.info("# Subscribe-1 : {}", data));

        Thread.sleep(2100L);

        hotSequence.subscribe(data -> log.info("# Subscribe-2 : {}", data));

        Thread.sleep(2100L);

        hotSequence.subscribe(data -> log.info("# Subscribe-3 : {}", data));

        Thread.sleep(1000L);
    }
}

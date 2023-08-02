package reactive.sequence;

import lombok.extern.slf4j.Slf4j;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Slf4j
public class ColdSequence {
    public static void main(String[] args) throws InterruptedException {
        Flux<String> coldSequence = Flux.just("a", "b", "c", "d", "e", "f", "g")
                .map(String::toUpperCase);

        Mono<String> coldSequence2 = Mono.just("HI!!")
                .map(String::toUpperCase);

        coldSequence.subscribe(log::info);
        coldSequence2.subscribe(log::info);

        Thread.sleep(1000L);

        coldSequence.subscribe(log::info);
        coldSequence2.subscribe(log::info);

        Thread.sleep(100L);
    }
}

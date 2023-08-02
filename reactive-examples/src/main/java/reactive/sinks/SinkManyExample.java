package reactive.sinks;

import lombok.extern.slf4j.Slf4j;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Sinks;

import java.time.Duration;

@Slf4j
public class SinkManyExample {
    public static void main(String[] args) {

        Sinks.Many<String> unicastSinks = Sinks.many().unicast().onBackpressureBuffer();

        unicastSinks.emitNext("Hello", Sinks.EmitFailureHandler.FAIL_FAST);
        unicastSinks.emitNext("World", Sinks.EmitFailureHandler.FAIL_FAST);

        Flux<String> flux = unicastSinks.asFlux();

        flux.subscribe(data -> log.info("{}", data));

//        flux.subscribe(data -> log.info("{}", data));

        Sinks.Many<String> multicastSinks = Sinks.many().multicast().onBackpressureBuffer();
        Flux<String> flux2 = multicastSinks.asFlux();


        multicastSinks.emitNext("Hello", Sinks.EmitFailureHandler.FAIL_FAST);
        multicastSinks.emitNext("World", Sinks.EmitFailureHandler.FAIL_FAST);

        flux2.subscribe(data -> log.info("# Subscriber-1 {}", data));

        flux2.subscribe(data -> log.info("# Subscriber-2 {}", data));

        multicastSinks.emitNext("Reactor", Sinks.EmitFailureHandler.FAIL_FAST);

        Sinks.Many<String> multicastReplaySinks = Sinks.many().replay().limit(1);
        Flux<String> flux3 = multicastReplaySinks.asFlux();

        multicastReplaySinks.emitNext("Hello", Sinks.EmitFailureHandler.FAIL_FAST);
        multicastReplaySinks.emitNext("World", Sinks.EmitFailureHandler.FAIL_FAST);

        flux3.subscribe(data -> log.info("# Replay Subscriber-1 {}", data));

        flux3.subscribe(data -> log.info("# Replay Subscriber-2 {}", data));

        multicastReplaySinks.emitNext("Reactor", Sinks.EmitFailureHandler.FAIL_FAST);

    }
}

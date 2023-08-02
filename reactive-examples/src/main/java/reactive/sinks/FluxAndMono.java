package reactive.sinks;

import lombok.extern.slf4j.Slf4j;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import reactor.core.publisher.Sinks;

@Slf4j
public class FluxAndMono {
    public static void main(String[] args) {
        Sinks.One<Object> sinksOne = Sinks.one();
        Mono<Object> mono = sinksOne.asMono();

        sinksOne.emitValue("Hello", Sinks.EmitFailureHandler.FAIL_FAST);

        mono.subscribe(data -> log.info("{}", data));

        Sinks.Many<String> manySinks = Sinks.many().unicast().onBackpressureBuffer();
        Flux<String> flux = manySinks.asFlux();

        manySinks.emitNext("Hello", Sinks.EmitFailureHandler.FAIL_FAST);
        manySinks.emitNext("World", Sinks.EmitFailureHandler.FAIL_FAST);

        flux.subscribe(data -> log.info("{}", data));

    }
}

package reactive.sinks;

import lombok.extern.slf4j.Slf4j;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Sinks;
import reactor.core.scheduler.Schedulers;

import java.util.stream.IntStream;

@Slf4j
public class SinksExample {
    public static void main(String[] args) throws InterruptedException {
        int tasks = 6;

        Sinks.Many<String> unicastSinks = Sinks.many()
                                            .unicast()
                                            .onBackpressureBuffer();

        Flux<String> flux = unicastSinks.asFlux();

        IntStream.range(1, tasks)
                .forEach(n -> {
                    try {
                      new Thread(() -> {
                          unicastSinks.emitNext(doTasks(n), Sinks.EmitFailureHandler.FAIL_FAST);
                          log.info("# emitted: {}", n);
                      }).start();

                      Thread.sleep(100L);

                    } catch (InterruptedException e) {
                        log.error(e.getMessage());
                    }
                });

        flux
            .publishOn(Schedulers.parallel())
                .map(result -> result + " success!!")
                .doOnNext(n -> log.info("# map(): {}", n))
                .publishOn(Schedulers.parallel())
                .subscribe(data -> log.info("# onNext() : {}", data));

        Thread.sleep(200L);
    }

    private static String doTasks(int taskNumber) {
        return "task" + taskNumber + " result";
    }
}

package reactive.sinks;

import lombok.extern.slf4j.Slf4j;
import reactor.core.publisher.Flux;
import reactor.core.publisher.FluxSink;
import reactor.core.scheduler.Schedulers;

import java.util.stream.IntStream;

@Slf4j
public class FluxSinkExample {
    public static void main(String[] args) throws InterruptedException {
        int tasks = 6;

        Flux.create((FluxSink<String> sink) -> {
                    IntStream.range(1, tasks)
                            .forEach(n -> sink.next(doTasks(n)));
                })
                .subscribeOn(Schedulers.parallel())
                .doOnNext(taskNumber -> log.info("# create() : {}", taskNumber))
                .publishOn(Schedulers.boundedElastic())
                .map(result -> result + " success!!")
                .doOnNext(taskNumber -> log.info("# map(): {}", taskNumber))
                .publishOn(Schedulers.parallel())
                .subscribe(result -> log.info("# onNext(): {}", result));

        Thread.sleep(1000L);
    }

    private static String doTasks(int taskNumber) {
        return "task" + taskNumber + " result";
    }
}

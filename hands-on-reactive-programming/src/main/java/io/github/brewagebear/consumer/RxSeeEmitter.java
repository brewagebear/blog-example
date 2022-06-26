package io.github.brewagebear.consumer;

import io.github.brewagebear.vo.Temperature;
import io.reactivex.subscribers.DisposableSubscriber;
import java.io.IOException;
import java.util.concurrent.atomic.AtomicInteger;
import lombok.extern.slf4j.Slf4j;
import org.reactivestreams.Subscriber;
import org.springframework.web.servlet.mvc.method.annotation.SseEmitter;


@Slf4j
public class RxSeeEmitter extends SseEmitter {
    private static final long SSE_SESSION_TIMEOUT = 30 * 60 * 1000L;
    private final DisposableSubscriber<Temperature> subscriber;
    private final static AtomicInteger sessionIdSequence = new AtomicInteger(0);
    private final int sessionId = sessionIdSequence.incrementAndGet();

    public RxSeeEmitter() {
        super(SSE_SESSION_TIMEOUT);

        this.subscriber = new DisposableSubscriber<Temperature>() {
            @Override
            public void onNext(Temperature temperature) {
                try {
                    RxSeeEmitter.this.send(temperature);
                } catch (IOException e) {
                    subscriber.dispose();
                }
            }

            @Override
            public void onError(Throwable t) {
                log.warn("[{}] Received sensor error: {}", sessionId, t.getMessage());
            }

            @Override
            public void onComplete() {
                log.info("[{}] Stream completed", sessionId);
            }
        };

        onTimeout(() -> {
            log.info("[{}] SSE TIMEOUT :", sessionId);
            subscriber.dispose();
        });

        onCompletion(() -> {
            log.info("[{}] SSE COMPLETED :", sessionId);
            subscriber.dispose();
        });

    }

    public Subscriber<Temperature> getSubscriber() {
        return subscriber;
    }
}

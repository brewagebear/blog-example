package io.github.brewagebear.producer;

import io.github.brewagebear.vo.Temperature;
import io.reactivex.Flowable;
import io.reactivex.Observable;
import java.util.Random;
import java.util.concurrent.TimeUnit;
import org.springframework.stereotype.Component;

@Component
public class TemperatureSensor {
    private final Random rand = new Random();

//    Observable은 ReactiveStream을 지원을 하지 않으며, 생산자 소비자 모두 Observable을 취한다.
//    따라서, Subscriber를 사용하기 위해서 Flowable (ReactiveStream)을 사용하여 처리
//    private final Observable<Temperature> dataStream =
//        Observable
//            .range(0, Integer.MAX_VALUE)
//            .concatMap(tick -> Observable
//                .just(tick)
//                .delay(rand.nextInt(5000), TimeUnit.MILLISECONDS)
//                .map(tickValue -> this.probe()))
//            .publish()
//            .refCount();

    private final Flowable<Temperature> flowStream =
        Flowable
            .range(0, Integer.MAX_VALUE)
            .concatMap(tick -> Flowable
                .just(tick)
                .delay(rand.nextInt(5000), TimeUnit.MILLISECONDS)
                .map(value -> this.probe()))
            .publish()
            .refCount();

    private Temperature probe() {
        return new Temperature(16 + rand.nextGaussian() * 10);
    }

    public Flowable<Temperature> temperatureStream() {
        return flowStream;
    }

}

package io.github.brewagebear.producer;

import io.github.brewagebear.vo.Temperature;
import java.util.Random;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;
import javax.annotation.PostConstruct;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Component;

@Component
public class TemperatureSensor {

    private final ApplicationEventPublisher publisher;
    private final Random rand = new Random();
    private final ScheduledExecutorService executor =
        Executors.newSingleThreadScheduledExecutor();

    public TemperatureSensor(ApplicationEventPublisher publisher) {
        this.publisher = publisher;
    }

    @PostConstruct
    public void startProcessing() {
        this.executor.schedule(this::probe, 1, TimeUnit.SECONDS);
    }

    private void probe() {
        double temperature = 16 + rand.nextGaussian() * 10;
        publisher.publishEvent(new Temperature(temperature));

        executor.schedule(this::probe, rand.nextInt(5000), TimeUnit.MILLISECONDS);
    }

}

package io.github.brewagebear.controller;

import io.github.brewagebear.consumer.RxSeeEmitter;
import io.github.brewagebear.producer.TemperatureSensor;
import io.github.brewagebear.vo.Temperature;
import io.reactivex.functions.Consumer;
import java.util.ArrayList;
import javax.servlet.http.HttpServletRequest;
import org.springframework.context.event.EventListener;
import org.springframework.http.MediaType;
import org.springframework.scheduling.annotation.Async;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.method.annotation.SseEmitter;

@RestController
public class TemperatureController {
    private final TemperatureSensor sensor;

    public TemperatureController(TemperatureSensor sensor) {
        this.sensor = sensor;
    }

    @GetMapping(value = "/")
    public ModelAndView index() {
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setViewName("index.html");
        return modelAndView;
    }

    @GetMapping(value = "/temperature-stream")
    public SseEmitter events(HttpServletRequest request) {
        RxSeeEmitter emitter = new RxSeeEmitter();

        sensor.temperatureStream()
            .safeSubscribe(emitter.getSubscriber());

        return emitter;
    }
}

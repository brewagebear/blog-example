package io.github.brewagebear.controller;

import io.github.brewagebear.vo.Temperature;
import java.util.ArrayList;
import java.util.Set;
import java.util.concurrent.CopyOnWriteArraySet;
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
    private final Set<SseEmitter> clients = new CopyOnWriteArraySet<>();

    @GetMapping(value = "/")
    public ModelAndView index() {
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setViewName("index.html");
        return modelAndView;
    }

    @GetMapping(value = "/temperature-stream")
    public SseEmitter events(HttpServletRequest request) {
        SseEmitter emitter = new SseEmitter();
        clients.add(emitter);

        emitter.onTimeout(() -> clients.remove(emitter));
        emitter.onCompletion(() -> clients.remove(emitter));

        return emitter;
    }


    @Async("threadpool")
    @EventListener
    public void handleMessage(Temperature temperature) {
        ArrayList<SseEmitter> deadEmitters = new ArrayList<>();

        clients.forEach(sseEmitter -> {
            try {
                sseEmitter.send(temperature, MediaType.APPLICATION_JSON);
            } catch (Exception e) {
                deadEmitters.add(sseEmitter);
            }
        });

        deadEmitters.forEach(clients::remove);
    }

}

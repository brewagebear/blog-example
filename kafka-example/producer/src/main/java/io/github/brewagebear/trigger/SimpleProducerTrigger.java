package io.github.brewagebear.trigger;


import brewagebear.avro.Student;
import io.github.brewagebear.usecase.SimpleProducerService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class SimpleProducerTrigger {
    private final SimpleProducerService simpleProducerService;

    public SimpleProducerTrigger(SimpleProducerService simpleProducerService) {
        this.simpleProducerService = simpleProducerService;
    }

    @GetMapping("/send")
    public String send(@RequestParam("name") String name, @RequestParam("class") int dd) {
        Student student = Student.newBuilder()
                .setName(name)
                .setClass$(dd)
                .build();

        simpleProducerService.sendMessage(student);
        return "Avro Message sent to the Kafka Topic successfully";
    }
}

package io.github.brewagebear.producer.config;

import io.github.brewagebear.producer.dto.Customer;
import java.util.concurrent.atomic.AtomicLong;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.JobBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
import org.springframework.batch.core.launch.support.RunIdIncrementer;
import org.springframework.batch.item.ItemReader;
import org.springframework.batch.item.NonTransientResourceException;
import org.springframework.batch.item.ParseException;
import org.springframework.batch.item.UnexpectedInputException;
import org.springframework.batch.item.kafka.KafkaItemWriter;
import org.springframework.batch.item.kafka.builder.KafkaItemWriterBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.core.KafkaTemplate;

@Configuration
public class JobConfiguration {

    private final JobBuilderFactory jobBuilderFactory;
    private final StepBuilderFactory stepBuilderFactory;
    private final KafkaTemplate<Long, Customer> kafkaTemplate;

    public JobConfiguration(JobBuilderFactory jobBuilderFactory,
        StepBuilderFactory stepBuilderFactory, KafkaTemplate<Long, Customer> kafkaTemplate) {
        this.jobBuilderFactory = jobBuilderFactory;
        this.stepBuilderFactory = stepBuilderFactory;
        this.kafkaTemplate = kafkaTemplate;
    }

    @Bean
    public Job inventoryJob() {
        return this.jobBuilderFactory.get("job")
            .start(inventoryStep())
            .incrementer(new RunIdIncrementer())
            .build();
    }

    @Bean
    public KafkaItemWriter<Long, Customer> kafkaItemWriter() {
        return new KafkaItemWriterBuilder<Long, Customer>()
            .kafkaTemplate(kafkaTemplate)
            .itemKeyMapper(Customer::getId)
            .build();
    }

    @Bean
    public Step inventoryStep() {

        var id = new AtomicLong();

        var reader = new ItemReader<Customer>() {
            @Override
            public Customer read()
                throws UnexpectedInputException, ParseException, NonTransientResourceException {
                if(id.incrementAndGet() < 10_1000)
                    return new Customer(id.get(), Math.random() > .5 ? "Jane" : "Jose");
                return null;
            }
        };

        return this.stepBuilderFactory.get("s1")
            .<Customer, Customer>chunk(10)
            .reader(reader)
            .writer(kafkaItemWriter())
            .build();
    }

}

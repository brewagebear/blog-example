package io.github.brewagebear.consumer.config;

import io.github.brewagebear.producer.dto.Customer;
import java.util.List;
import java.util.Properties;
import lombok.extern.slf4j.Slf4j;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.JobBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
import org.springframework.batch.core.launch.support.RunIdIncrementer;
import org.springframework.batch.item.ItemWriter;
import org.springframework.batch.item.kafka.KafkaItemReader;
import org.springframework.batch.item.kafka.builder.KafkaItemReaderBuilder;
import org.springframework.boot.autoconfigure.kafka.KafkaProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Slf4j
@Configuration
public class ConsumerJobConfiguration {

    private final KafkaProperties properties;

    private final JobBuilderFactory jobBuilderFactory;

    private final StepBuilderFactory stepBuilderFactory;

    public ConsumerJobConfiguration(KafkaProperties properties, JobBuilderFactory jobBuilderFactory,
        StepBuilderFactory stepBuilderFactory) {
        this.properties = properties;
        this.jobBuilderFactory = jobBuilderFactory;
        this.stepBuilderFactory = stepBuilderFactory;
    }

    @Bean
    KafkaItemReader<Long, Customer> kafkaItemReader() {
        var props = new Properties();
        props.putAll(this.properties.buildConsumerProperties());

        return new KafkaItemReaderBuilder<Long, Customer>()
            .partitions(0)
            .consumerProperties(props)
            .name("customers-reader")
            .saveState(true)
            .topic("customers")
            .build();
    }

    @Bean
    public Job consumerJob() {
        return jobBuilderFactory.get("job")
            .incrementer(new RunIdIncrementer())
            .start(consumerStep())
            .build();
    }

    @Bean
    public Step consumerStep() {

        var writer = new ItemWriter<>() {
            @Override
            public void write(List<?> items) throws Exception {
                items.forEach(item -> log.info("new Customer : " + item));
            }
        };

        return stepBuilderFactory.get("step")
            .<Customer, Customer>chunk(10)
            .writer(writer)
            .reader(kafkaItemReader())
            .build();
    }

}

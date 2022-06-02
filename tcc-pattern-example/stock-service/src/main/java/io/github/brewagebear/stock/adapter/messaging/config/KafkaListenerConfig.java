package io.github.brewagebear.stock.adapter.messaging.config;

import static io.github.brewagebear.stock.constants.KafkaConstant.DEAD_TOPIC_DESTINATION_RESOLVER;

import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.autoconfigure.kafka.KafkaProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.config.ConcurrentKafkaListenerContainerFactory;
import org.springframework.kafka.config.KafkaListenerContainerFactory;
import org.springframework.kafka.core.ConsumerFactory;
import org.springframework.kafka.core.DefaultKafkaConsumerFactory;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.listener.AfterRollbackProcessor;
import org.springframework.kafka.listener.CommonErrorHandler;
import org.springframework.kafka.listener.ConcurrentMessageListenerContainer;
import org.springframework.kafka.listener.ContainerProperties.AckMode;
import org.springframework.kafka.listener.DeadLetterPublishingRecoverer;
import org.springframework.kafka.listener.DefaultAfterRollbackProcessor;
import org.springframework.kafka.listener.DefaultErrorHandler;
import org.springframework.util.backoff.FixedBackOff;

@Slf4j
@Configuration
public class KafkaListenerConfig {

    private final KafkaProperties properties;

    private final KafkaTemplate<Long, String> template;

    public KafkaListenerConfig(KafkaProperties properties, KafkaTemplate<Long, String> template) {
        this.properties = properties;
        this.template = template;
    }

    @Bean
    public KafkaListenerContainerFactory<ConcurrentMessageListenerContainer<String, String>> kafkaListenerContainerFactory() {
        ConcurrentKafkaListenerContainerFactory<String, String> factory = new ConcurrentKafkaListenerContainerFactory<>();
        factory.setConsumerFactory(consumerFactory());
        factory.setCommonErrorHandler(kafkaListenerErrorHandler());
        factory.getContainerProperties().setAckMode(AckMode.MANUAL_IMMEDIATE);
        return factory;
    }

    @Bean
    public CommonErrorHandler kafkaListenerErrorHandler() {
        DefaultErrorHandler defaultErrorHandler = new DefaultErrorHandler(
            new DeadLetterPublishingRecoverer(template, DEAD_TOPIC_DESTINATION_RESOLVER),
            new FixedBackOff(1000, 3));

        defaultErrorHandler.setCommitRecovered(true);
        defaultErrorHandler.setAckAfterHandle(true);
        /***
         * 	If the recoverer fails (throws an exception), the failed record will be included in the seeks.
         * 	If the recoverer fails, the BackOff will be reset by default and redeliveries will again go through the back offs before recovery is attempted again.
         * 	To skip retries after a recovery failure, set the error handler’s resetStateOnRecoveryFailure to false.
         */
        defaultErrorHandler.setResetStateOnRecoveryFailure(false);

        return defaultErrorHandler;
    }

    private AfterRollbackProcessor<String, String> afterRollbackProcessor() {
        return new DefaultAfterRollbackProcessor<>(
            new DeadLetterPublishingRecoverer(template, DEAD_TOPIC_DESTINATION_RESOLVER),
            new FixedBackOff(1000, 3));
    }

    private ConsumerFactory<String, String> consumerFactory() {
        return new DefaultKafkaConsumerFactory<>(this.properties.buildConsumerProperties());
    }

}

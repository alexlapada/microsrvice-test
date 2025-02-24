package ua.lapada.app.university.configuration.rabitmq;

import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class RabbitMqConfiguration {

    @Bean
    RabbitMqProducer producer(RabbitTemplate template) {
        return new RabbitMqProducer(template);
    }
}

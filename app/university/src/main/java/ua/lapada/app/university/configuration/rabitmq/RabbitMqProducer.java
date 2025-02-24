package ua.lapada.app.university.configuration.rabitmq;

import lombok.AllArgsConstructor;
import org.springframework.amqp.rabbit.core.RabbitTemplate;

@AllArgsConstructor
public class RabbitMqProducer {
    private final RabbitTemplate template;

    public void send(String queue, Object payload) {
        template.convertAndSend(queue, payload);
    }
}

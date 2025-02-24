package ua.lapada.app.university.integration.rabitmq;

import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

@Component
public class TestConsumer {

//    @RabbitListener(queues = "myQueue")
//    public void listen(String in) {
//        System.out.println("Message read from myQueue : " + in);
//    }
}

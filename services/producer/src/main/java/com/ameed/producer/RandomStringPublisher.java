package com.ameed.producer;

import com.ameed.msg.CustomMessage;
import org.springframework.amqp.core.AmqpTemplate;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import static com.ameed.config.CustomRabbitConfig.*;

@Service
public class RandomStringPublisher {

    private final AmqpTemplate template;
    private int counter;

    public RandomStringPublisher(AmqpTemplate template) {
        this.template = template;
    }

    @Scheduled(fixedDelay = 1)
    public void publish() {
        int id = counter++;
        CustomMessage message = new CustomMessage(id, "Message #" + id);
        template.convertAndSend(EXCHANGE_NAME, BINDING_KEY, message);
    }
}

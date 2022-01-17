package com.ameed.consumer;

import com.ameed.msg.CustomMessage;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.core.AmqpTemplate;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Service;

import static com.ameed.config.CustomRabbitConfig.*;

@Service
@Slf4j
public class LengthCalculator {

    private final AmqpTemplate template;

    public LengthCalculator(AmqpTemplate template) {
        this.template = template;
    }

    @RabbitListener(
            queues = QUEUE_NAME1,
            containerFactory = "prefetchOne"
    )
    public void calculateLength(CustomMessage message) throws InterruptedException {
        log.info("{}  ---   The length of '{}' is {}",
                 message.getId(),
                 message.getContent(),
                 message.getContent().length());
        template.convertAndSend(EXCHANGE_NAME, BINDING_KEY2, message);
    }
}

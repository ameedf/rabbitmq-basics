package com.ameed.consumer;

import com.ameed.msg.CustomMessage;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Service;

import static com.ameed.config.CustomRabbitConfig.QUEUE_NAME2;

@Service
@Slf4j
public class LengthCalculator {

    @RabbitListener(
            queues = QUEUE_NAME2,
            containerFactory = "prefetchOne"
    )
    public void calculateLength(CustomMessage message) throws InterruptedException {
        log.info("{}  ---   The length of '{}' is {}",
                 message.getId(),
                 message.getContent(),
                 message.getContent().length());
    }
}

package com.ameed.consumer;

import com.ameed.msg.CustomMessage;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Service;

import java.util.concurrent.TimeUnit;

import static com.ameed.config.CustomRabbitConfig.QUEUE_NAME2;

@Service
@Slf4j
public class UpperCaseService {

    @RabbitListener(
            queues = QUEUE_NAME2,
            containerFactory = "prefetchOne"
    )
    public void toUpperCase(CustomMessage message) throws InterruptedException {
        log.info("{}  ---   '{}' --> {}",
                 message.getId(),
                 message.getContent(),
                 message.getContent().toUpperCase());
        TimeUnit.MILLISECONDS.sleep(1);
    }
}

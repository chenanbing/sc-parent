package com.cab.scservice.mq.amqp;

import org.springframework.amqp.rabbit.annotation.*;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

@Component
@RabbitListener(queues = "myQueue")
public class AmqpConsumer {

    @RabbitHandler
    public void process(String hello) {
        System.out.println("HelloSubscriber : " + hello);
    }

}

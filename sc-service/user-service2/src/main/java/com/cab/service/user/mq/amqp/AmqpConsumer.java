package com.cab.service.user.mq.amqp;

import org.springframework.amqp.rabbit.annotation.*;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

//@Component
//@RabbitListener(queues = "myQueue")
//public class AmqpConsumer {
//
//    @RabbitHandler
//    public void process(String hello) {
//        System.out.println("HelloSubscriber : " + hello);
//    }
//
//}


@Component
public class AmqpConsumer {

    //绑定队列名称
    @RabbitListener(queues = "myQueue")
    public void receive(String message) {
        System.out.println("HelloSubscriber : " + message);
    }

}

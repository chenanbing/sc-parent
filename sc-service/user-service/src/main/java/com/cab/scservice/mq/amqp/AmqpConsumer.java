package com.cab.scservice.mq.amqp;

import org.springframework.amqp.rabbit.annotation.RabbitHandler;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

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

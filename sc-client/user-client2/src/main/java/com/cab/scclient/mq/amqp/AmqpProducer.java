package com.cab.scclient.mq.amqp;

import org.springframework.amqp.core.AmqpTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Date;

@Component
public class AmqpProducer {
    @Autowired
    private AmqpTemplate rabbitTemplate;

    public void sendMsg() {
        String context = "hello " + new Date();
        this.rabbitTemplate.convertAndSend("myExchange","myQueue",context);
        System.err.println("消息发送成功："+context);
    }
}

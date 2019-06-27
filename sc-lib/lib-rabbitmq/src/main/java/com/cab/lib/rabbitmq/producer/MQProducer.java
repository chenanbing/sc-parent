package com.cab.lib.rabbitmq.producer;

import org.springframework.amqp.core.AmqpTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * @Description:
 * @Auther: Administrator
 * @Date: 2018/9/10
 */

@Component
public class MQProducer {

    @Autowired
    private AmqpTemplate rabbitTemplate;

    public void sendString(String queueName,String context){

        this.rabbitTemplate.convertAndSend(queueName,context);

    }
}

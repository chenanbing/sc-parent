package com.cab.service.user.mq.stream;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.stream.annotation.EnableBinding;
import org.springframework.cloud.stream.annotation.StreamListener;
import org.springframework.stereotype.Component;

@Component
@EnableBinding(value = {MqMessageSink.class})
public class MqMessageConsumer {

    @Autowired
    MqMessageProducer mqMessageProducer;

    @StreamListener(MqMessageSink.INPUT1)
    public synchronized void receive1(String message) {
        System.out.println("******************");
        System.out.println("At Sink1");
        System.out.println("******************");
        System.out.println("Received message " + message);

        mqMessageProducer.sendMsg("sdfasdf",MqMessageSource.OUTPUT3);
    }

    @StreamListener(MqMessageSink.INPUT2)
    public synchronized void receive2(String message) {
        System.out.println("******************");
        System.out.println("At Sink2");
        System.out.println("******************");
        System.out.println("Received message " + message);
    }

    @StreamListener(MqMessageSink.INPUT3)
    public synchronized void receive3(String message) {
        System.out.println("******************");
        System.out.println("At Sink3");
        System.out.println("******************");
        System.out.println("Received message " + message);
    }

}

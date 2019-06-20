package com.cab.scservice.mq.stream;

import org.springframework.cloud.stream.annotation.EnableBinding;
import org.springframework.cloud.stream.annotation.StreamListener;
import org.springframework.stereotype.Component;

@Component
@EnableBinding(value = {MqMessageSink.class})
public class MqMessageConsumer {

    @StreamListener(MqMessageSink.INPUT1)
    public synchronized void receive1(String message) {
        System.out.println("******************");
        System.out.println("At Sink1");
        System.out.println("******************");
        System.out.println("Received message " + message);
    }

    @StreamListener(MqMessageSink.INPUT2)
    public synchronized void receive2(String message) {
        System.out.println("******************");
        System.out.println("At Sink2");
        System.out.println("******************");
        System.out.println("Received message " + message);
    }


}

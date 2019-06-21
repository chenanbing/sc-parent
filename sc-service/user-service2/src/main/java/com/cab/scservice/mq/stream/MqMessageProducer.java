package com.cab.scservice.mq.stream;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.stream.annotation.EnableBinding;
import org.springframework.cloud.stream.binding.BinderAwareChannelResolver;
import org.springframework.messaging.support.MessageBuilder;

@EnableBinding(MqMessageSource.class)
public class MqMessageProducer {

    @Autowired
    private BinderAwareChannelResolver resolver;

    @Autowired
    private MqMessageSource mqMessageSource;


    public void sendMsg(String msg, String target) {
        resolver.resolveDestination(target).send(MessageBuilder.withPayload(msg).build());
    }

}
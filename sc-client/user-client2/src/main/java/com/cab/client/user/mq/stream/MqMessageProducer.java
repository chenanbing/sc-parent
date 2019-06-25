package com.cab.client.user.mq.stream;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.stream.annotation.EnableBinding;
import org.springframework.cloud.stream.binding.BinderAwareChannelResolver;
import org.springframework.messaging.support.MessageBuilder;

@EnableBinding(com.cab.scclient.mq.stream.MqMessageSource.class)
public class MqMessageProducer {

    @Autowired
    private BinderAwareChannelResolver resolver;

    @Autowired
    private com.cab.scclient.mq.stream.MqMessageSource mqMessageSource;


    public void sendMsg(String msg, String target) {
        resolver.resolveDestination(target).send(MessageBuilder.withPayload(msg).build());
    }


    public void sendMsg1(String msg) {
        mqMessageSource.output1().send(
                MessageBuilder.withPayload(msg).build());
    }

    public void sendMsg2(String msg) {
        mqMessageSource.output2().send(
                MessageBuilder.withPayload(msg).build());
    }




}

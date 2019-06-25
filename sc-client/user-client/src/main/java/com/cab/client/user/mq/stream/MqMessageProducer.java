package com.cab.client.user.mq.stream;

import com.cab.scclient.mq.stream.MqMessageSource;
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


    public void sendMsg1(String msg) {
        mqMessageSource.output1().send(
                MessageBuilder.withPayload(msg).build());
    }

    public void sendMsg2(String msg) {
        mqMessageSource.output2().send(
                MessageBuilder.withPayload(msg).build());
    }




}

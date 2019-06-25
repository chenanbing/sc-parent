package com.cab.service.user.mq.stream;

import org.springframework.cloud.stream.annotation.Output;
import org.springframework.messaging.MessageChannel;

public interface MqMessageSource  {

    String OUTPUT3 = "output3";

    @Output(OUTPUT3)
    MessageChannel output3();

}


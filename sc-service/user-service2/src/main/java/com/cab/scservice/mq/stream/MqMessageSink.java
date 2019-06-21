package com.cab.scservice.mq.stream;

import org.springframework.cloud.stream.annotation.Input;
import org.springframework.messaging.SubscribableChannel;

public interface MqMessageSink {

    String INPUT1 = "input1";

    String INPUT2 = "input2";

    String INPUT3 = "input3";

    @Input(INPUT1)
    SubscribableChannel input1();

    @Input(INPUT2)
    SubscribableChannel input2();

    @Input(INPUT3)
    SubscribableChannel input3();

}

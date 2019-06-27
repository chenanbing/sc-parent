package com.cab.lib.rabbitmq.config;

import org.springframework.amqp.core.Queue;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @Description:
 * @Auther: Administrator
 * @Date: 2018/9/10
 */

@Configuration
public class RabbitConfig {

    @Bean
    public Queue Queue() {

        return new Queue(MqQueueNames.HELLO);
    }


}

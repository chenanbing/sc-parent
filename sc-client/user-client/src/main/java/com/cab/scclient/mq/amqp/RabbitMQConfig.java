package com.cab.scclient.mq.amqp;

import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.BindingBuilder;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.core.TopicExchange;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class RabbitMQConfig {

    /**
     * rabbitMq里初始化exchange.
     *
     * @return
     */
    @Bean
    public TopicExchange exchange() {
        return new TopicExchange("myExchange");
    }

    /**
     * rabbitMq里初始化队列myQueue.
     *
     * @return
     */
    @Bean
    public Queue queue() {
        return new Queue("myQueue");
    }

    /**
     * 绑定exchange & queue & routekey.
     *
     * @return
     */
    @Bean
    public Binding bindingExchange() {
        return BindingBuilder.bind(queue()).to(exchange()).with("myQueue.#");
    }


}

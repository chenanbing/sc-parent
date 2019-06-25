package com.cab.client.user.controller;

import com.cab.scclient.feign.UserClient;
import com.cab.scclient.mq.amqp.AmqpProducer;
import com.cab.scclient.mq.stream.MqMessageProducer;
import com.cab.scclient.mq.stream.MqMessageSource;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class UserClientController {

    static Logger log = LogManager.getLogger(UserClientController.class);

    @Autowired
    UserClient userClient;

    @Autowired
    MqMessageProducer mqMessageProducer;

    @Autowired
    AmqpProducer amqpProducer;


    @RequestMapping(value = "/user/getById" )
    public String getById(){
        return userClient.get(8L);
    }

    /**
     * 1.如果在配置文件中未配置消费者组，系统会自动生成一个临时队列，连接断开，队列消失，队列名:myTest.anonymous.fsd15hu64....随机生成
     * 2.如果配置了消费者组，则自动生成队列为: myTest.消费者组名称 (非临时队列)
     * 3.默认发送消息，会向所有消费者组推送消息，如果一个有多个消费者 在 同一个消费者组里，消息会轮询发给这个组里的消费者
     * @return
     */
    @RequestMapping(value = "/user/mySend1")
    public boolean mySend1(){
//        mqMessageProducer.sendMsg("asdfasdfasdfasdfddddddd", MqMessageSource.OUTPUT1);
        mqMessageProducer.sendMsg1("asdfasdfasdfasdfddddddd");
        return true;
    }

    @RequestMapping(value = "/user/mySend2")
    public boolean mySend2(){
//        mqMessageProducer.sendMsg("asdfasdfasdfasdfddddddd", MqMessageSource.OUTPUT2);
        mqMessageProducer.sendMsg2("asdfasdfasdfasdfddddddd");
        return true;
    }

    @RequestMapping(value = "/user/mySend3")
    public boolean mySend3(){
        mqMessageProducer.sendMsg("asdfasdfasdfasdfddddddd", MqMessageSource.OUTPUT1);
        return true;
    }

    @RequestMapping(value = "/user/mySend4")
    public boolean mySend4(){
        mqMessageProducer.sendMsg("asdfasdfasdfasdfddddddd", MqMessageSource.OUTPUT2);
        return true;
    }


    @RequestMapping(value = "/user/mySend")
    public void send() {
//        try{
//            //睡5秒，网关Hystrix3秒超时，会触发熔断降级操作
//            Thread.sleep(5000);
//        }catch (Exception e){
//            e.printStackTrace();
//        }

        this.amqpProducer.sendMsg();
    }
}

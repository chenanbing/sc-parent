package com.cab.scclient.config;

import org.springframework.context.annotation.Configuration;

@Configuration
public class Configure {
/*
feign重试
1、feign:
       client:
           config:
             default:
               connectTimeout: 7000
               readTimeout: 7000
2、配置Retryer
因为用了feign肯定会用到ribbon，所以feign的重试机制相对来说比较鸡肋，自己feignClient的时候一般会关闭该功能。
    */
//    @Bean
//    public Retryer feignRetryer(){
//        Retryer retryer = new Retryer.Default(100, 1000, 4);
//        return retryer;
//    }

}

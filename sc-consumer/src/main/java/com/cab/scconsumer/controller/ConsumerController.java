package com.cab.scconsumer.controller;

import com.cab.scconsumer.feign.UserClient;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(value = "/consumer")
public class ConsumerController {

    static Logger log = LogManager.getLogger(ConsumerController.class);

    @Autowired
    UserClient userClient;


    @RequestMapping(value = "/user/getById" )
    public String getById(){
        return userClient.getOne(8L);
    }
}

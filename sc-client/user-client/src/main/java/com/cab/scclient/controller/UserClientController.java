package com.cab.scclient.controller;

import com.cab.scclient.feign.UserClient;
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


    @RequestMapping(value = "/user/getById" )
    public String getById(){
        return userClient.get(8L);
    }
}

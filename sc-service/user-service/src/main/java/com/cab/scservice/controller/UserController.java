package com.cab.scservice.controller;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class UserController {

    private static Logger log = LogManager.getLogger(UserController.class);

    @RequestMapping(value = "/getById")
    public String get(Long id){
        try {
            Thread.sleep(6000);
        }catch (Exception e){

        }
        System.out.println("USER"+id);

        String str = "USER"+id;

//        Integer.parseInt(str);

        return "USER"+id;

    }

}

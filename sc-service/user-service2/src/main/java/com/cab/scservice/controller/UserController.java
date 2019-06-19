package com.cab.scservice.controller;

import com.cab.scservice.constant.ConstantConfig;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class UserController {

    private static Logger log = LogManager.getLogger(UserController.class);

    @Autowired
    private ConstantConfig constantConfig;




    @RequestMapping(value = "/getById")
    public String get(@RequestParam(value="id",required=false) Long id){
        try {
//            Thread.sleep(6000);
        }catch (Exception e){

        }
        System.out.println("USER"+id);

        String str = "USER"+id;

//        Integer.parseInt(str);

        return "USER"+id+constantConfig.getVersion();

    }

}

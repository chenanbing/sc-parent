package com.cab.scconsumer.feign;

import com.cab.scconsumer.feign.fallBack.UserClientFallBack;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@FeignClient(name = "user",url = "http://localhost:8861/user",fallbackFactory = UserClientFallBack.class)
public interface  UserClient {

    @RequestMapping(method = RequestMethod.GET,value = "/getById")
    String getOne(Long id);

}

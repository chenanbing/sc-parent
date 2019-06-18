package com.cab.scclient.feign;

import com.cab.scclient.feign.fallBack.UserClientFallBackFactory;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@FeignClient(name = "user-service", fallbackFactory = UserClientFallBackFactory.class)
public interface  UserClient {

    @RequestMapping(value = "/getById")
    public String get(@RequestParam("id") Long id);

}

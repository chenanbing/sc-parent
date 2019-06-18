package com.cab.scconsumer.feign.fallBack;

import com.cab.scconsumer.feign.UserClient;
import feign.hystrix.FallbackFactory;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.stereotype.Component;

@Component
public class UserClientFallBack implements FallbackFactory<UserClient> {

    static Logger log = LogManager.getLogger(UserClientFallBack.class);

    @Override
    public UserClient create(Throwable throwable) {
        return new UserClient() {


            @Override
            public String getOne(Long id) {
                return null;
            }

        };

    }
}

package com.cab.scclient.feign.fallBack;

import com.cab.scclient.feign.UserClient;
import feign.hystrix.FallbackFactory;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.stereotype.Component;

@Component
public class UserClientFallBackFactory implements FallbackFactory<UserClient> {

    static Logger log = LogManager.getLogger(UserClientFallBackFactory.class);

    @Override
    public UserClient create(Throwable throwable) {
        return new UserClient() {
            @Override
            public String get(Long id) {
                log.error("-------UserClientFallBackFactory------get--------");
                return null;
            }
        };

    }
}

package com.cab.client.user.feign.fallBack;

import com.cab.client.user.feign.UserClient;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.stereotype.Service;

@Service
public class UserClientFallback implements UserClient {

    static Logger log = LogManager.getLogger(UserClientFallback.class);

    @Override
    public String get(Long id) {
        log.error("-------UserClientFallback------get--------");
        return null;
    }
}

package com.cab.scclient.feign.fallBack;

import com.cab.scclient.feign.UserClient;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.stereotype.Service;

@Service
public class UserClientFallback implements UserClient {

    static Logger log = LogManager.getLogger(UserClientFallBackFactory.class);

    @Override
    public String get(Long id) {
        log.error("-------UserClientFallback------get--------");
        return null;
    }
}

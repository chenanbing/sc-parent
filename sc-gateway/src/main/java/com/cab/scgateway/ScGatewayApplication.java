package com.cab.scgateway;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.zuul.EnableZuulProxy;

@EnableZuulProxy
@SpringBootApplication
public class ScGatewayApplication {

    public static void main(String[] args) {
        SpringApplication.run(ScGatewayApplication.class, args);
    }

}

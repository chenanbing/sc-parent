package com.cab.scgateway;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.zuul.EnableZuulProxy;

@EnableZuulProxy
@SpringBootApplication
public class ScZuulApplication {

    public static void main(String[] args) {
        SpringApplication.run(ScZuulApplication.class, args);
    }

}

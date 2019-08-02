package com.cab.eureka.config;


import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;

/**
 * spring boot 2.0.x以后eureka添加验证后出现了服务无法注册的问题，
 * 需要关闭csrf spring2.x版本的security默认启用了csrf检验，
 * 要在eurekaServer端配置security的csrf检验为false，
 */
@EnableWebSecurity
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {
    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.csrf().disable();//关闭csrf
        super.configure(http);
    }
}

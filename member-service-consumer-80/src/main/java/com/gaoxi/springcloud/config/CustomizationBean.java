package com.gaoxi.springcloud.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestTemplate;

//配置类，配置注入一个RestTemplate对象/bean
@Configuration
public class CustomizationBean {
    //说明：配置注入RestTemplate bean/对象
    @Bean
    public RestTemplate getRestTemplate() {
        return new RestTemplate();
    }
}

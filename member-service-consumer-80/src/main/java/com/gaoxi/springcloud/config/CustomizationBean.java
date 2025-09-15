package com.gaoxi.springcloud.config;

import org.springframework.cloud.client.loadbalancer.LoadBalanced;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestTemplate;

//配置类，配置注入一个RestTemplate对象/bean
@Configuration
public class CustomizationBean {
    //说明：配置注入RestTemplate bean/对象
    @Bean
    @LoadBalanced //为restTemplate赋予负载均衡的能力，默认是使用轮询算法来访问远程调用接口/地址
    public RestTemplate getRestTemplate() {
        return new RestTemplate();
    }
}

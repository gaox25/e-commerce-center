package com.gaoxi.springcloud;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

@SpringBootApplication
@EnableDiscoveryClient //引入的是启动nacos发现注解
public class MemberNacosConsumerApplication80 {
    public static void main(String[] args) {
        SpringApplication.run(MemberNacosConsumerApplication80.class, args);
    }
}

package com.gaoxi.springcloud;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

@SpringBootApplication
@EnableDiscoveryClient //启动Nacos发现的注解
public class MemberNacosProviderApplication10004 {
    public static void main(String[] args) {
        SpringApplication.run(MemberNacosProviderApplication10004.class, args);
    }
}

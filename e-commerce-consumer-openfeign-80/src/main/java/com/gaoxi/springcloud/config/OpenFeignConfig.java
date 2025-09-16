package com.gaoxi.springcloud.config;

import feign.Logger;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class OpenFeignConfig {
    //关闭OpenFeign日志
    @Bean
    public Logger.Level loggerLevel() {
        return Logger.Level.FULL;//指定日志级别
    }
}

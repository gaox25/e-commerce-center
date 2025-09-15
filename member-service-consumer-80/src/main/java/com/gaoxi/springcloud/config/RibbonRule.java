package com.gaoxi.springcloud.config;

import com.netflix.loadbalancer.IRule;
import com.netflix.loadbalancer.RandomRule;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

//在这里配置自己的负载均衡算法
@Configuration
public class RibbonRule {
    //配置注入自己的一个均衡算法
    @Bean
    public IRule myRibbonRule() {
        //这里返回的是RandomRule，当然也可以根据业务自己指定
        return new RandomRule();
    }
}

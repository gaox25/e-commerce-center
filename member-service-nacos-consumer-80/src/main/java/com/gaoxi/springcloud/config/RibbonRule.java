package com.gaoxi.springcloud.config;

import com.netflix.loadbalancer.IRule;
import com.netflix.loadbalancer.RandomRule;
import org.springframework.context.annotation.Configuration;

//@Configuration
//public class RibbonRule {
//    //配置注入自己的负载均衡算法
//    public IRule myRibbonRule() {
//        //这里返回的RandomRule，也可以指定其他的负载均衡算法
//        return new RandomRule();
//    }
//}

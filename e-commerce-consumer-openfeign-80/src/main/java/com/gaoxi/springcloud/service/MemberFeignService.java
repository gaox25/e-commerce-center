package com.gaoxi.springcloud.service;

import com.gaoxi.springcloud.entity.Result;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@Component
@FeignClient(value = "MEMBER-SERVICE-PROVIDER")
public interface MemberFeignService {
    //定义方法-就是远程调用的接口
    /*
     1.远程调用的方式是get
     2.远程调用的url http://ip:port + /member/get/{id}
       其中的ip:port部分，就是由注解@FeignClient中的value决定的
       所以最终调用的地址为：http://MEMBER-SERVICE-PROVIDER + /member/get/{id}
     3.MEMBER-SERVICE-PROVIDER 就是服务提供方在Eureka Server注册的服务名
     4.OpenFeign会根据负载均衡来决定调用10000还是10002，默认是轮询
     5.OpenFeign好处是支持Spring MVC的注解，加上是接口，进行了解耦
     */
    @GetMapping("/member/get/{id}")
    public Result getMemberById(@PathVariable("id") Long id);
}

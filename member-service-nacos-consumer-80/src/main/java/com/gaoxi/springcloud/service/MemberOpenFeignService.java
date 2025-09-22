package com.gaoxi.springcloud.service;

import com.gaoxi.springcloud.entity.Result;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient(value = "member-service-nacos-provider",
             fallback = MemberFeignFallbackService.class)
public interface MemberOpenFeignService {
    /*
     1.远程调用方式是GET方式
     2.远程调用的url为：http://member-service-nacos-provider/member/get/{id}
     3.member-service-nacos-provider 是nacos注册中心的服务名
     4.openfeign会根据负载均衡算法决定调用的是10004还是10006，默认负载均衡算法是轮询算法
     5.openfeign是通过接口方式来调用远程服务
     */
    @GetMapping("/member/get/{id}")
    public Result getMemberById(@PathVariable("id") Long id);
}

package com.gaoxi.springcloud.controller;

import com.gaoxi.springcloud.entity.Member;
import com.gaoxi.springcloud.entity.Result;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import javax.annotation.Resource;

import static jdk.nashorn.internal.runtime.regexp.joni.Config.log;

@RestController
@Slf4j
public class MemberNacosConsumerController {

    //说明：http://member-service-nacos-provider就是服务注册到Nacos Server的服务名，
    //     这里是小写，不同于Eureka的大写
    public static final String MEMBER_SERVICE_NACOS_PROVIDER_URL =
            "http://member-service-nacos-provider";

    @Resource
    private RestTemplate restTemplate;

    //方法/接口 添加Member对象到数据库里的表
    @PostMapping("/member/nacos/consumer/save")
    public Result<Member> save(Member member) {
        //拼接请求地址http://localhost:10000/member/save
        //member:就是通过restTemplate发出的post请求携带数据(对象)
        //Result.class:返回对象类型
        return restTemplate.postForObject(MEMBER_SERVICE_NACOS_PROVIDER_URL + "/member/save", member, Result.class);
    }

    //方法/接口，根据id，调用服务接口，返回member对象信息
    @GetMapping("/member/nacos/consumer/get/{id}")
    public Result<Member> getMemberById(@PathVariable("id") Long id) {
        return restTemplate.getForObject(
                MEMBER_SERVICE_NACOS_PROVIDER_URL + "/member/get/" + id, Result.class);
    }

}

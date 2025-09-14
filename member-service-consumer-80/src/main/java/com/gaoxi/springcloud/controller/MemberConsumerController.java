package com.gaoxi.springcloud.controller;

import com.gaoxi.springcloud.entity.Member;
import com.gaoxi.springcloud.entity.Result;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.client.SimpleClientHttpRequestFactory;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;
import javax.annotation.Resource;


@RestController
@Slf4j
public class MemberConsumerController {
    //定义member_service_provider_url，这是一个基础url地址
    //使用command + shift + U，将所选内容转为大写
    public static final String MEMBER_SERVICE_PROVIDER_URL =
            "http://localhost:10000";
    private static final Logger log = LoggerFactory.getLogger(MemberConsumerController.class);

    //装配RestTemplate，因为已经配置过了
    @Resource
    private RestTemplate restTemplate;

    //方法/接口 添加Member对象到数据库里的表
    @PostMapping("/member/consumer/save")
    public Result<Member> save(Member member) {
        System.out.println(MEMBER_SERVICE_PROVIDER_URL + "/member/save");
        log.info("service-consumer member={}", member);

        //拼接请求地址http://localhost:10000/member/save
        //member:就是通过restTemplate发出的post请求携带数据(对象)
        //Result.class:返回对象类型
        return restTemplate.postForObject(MEMBER_SERVICE_PROVIDER_URL + "/member/save", member, Result.class);
    }

    //方法/接口，根据id，调用服务接口，返回member对象信息
    @GetMapping("/member/consumer/get/{id}")
    public Result<Member> getMemberById(@PathVariable("id") Long id) {
        return restTemplate.getForObject(
                MEMBER_SERVICE_PROVIDER_URL + "/member/get/" + id, Result.class);
    }

}

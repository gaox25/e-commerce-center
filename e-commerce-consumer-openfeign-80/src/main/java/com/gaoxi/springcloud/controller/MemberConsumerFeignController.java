package com.gaoxi.springcloud.controller;

import com.gaoxi.springcloud.entity.Result;
import com.gaoxi.springcloud.service.MemberFeignService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

@RestController
public class MemberConsumerFeignController {

    //装配MemberFeignService
    @Resource
    private MemberFeignService memberFeignService;

    @GetMapping("/member/consumer/openfeign/get/{id}")
    public Result getMemberById(@PathVariable("id") Long id){
        return memberFeignService.getMemberById(id);
    }
}

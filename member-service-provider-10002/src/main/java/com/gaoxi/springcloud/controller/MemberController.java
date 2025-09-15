package com.gaoxi.springcloud.controller;

import com.gaoxi.springcloud.entity.Member;
import com.gaoxi.springcloud.entity.Result;
import com.gaoxi.springcloud.service.MemberService;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;

@Slf4j
@RestController
public class MemberController {
    private static final Logger log = LoggerFactory.getLogger(MemberController.class);
    //装配MemberService
    @Resource
    private MemberService memberService;

    /* 注意事项和细节
     1.前端如果是以json格式来发送添加信息member，那么需要使用@RequestBody，才能将数据封装到
       对应的bean中，同时保证http的请求头的content-type是对应
     2.如果前端是以表单形式或parameter形式提交了，则不需要使用@RequestBody，才会进行对象参数封装，
       同时保证http的请求头的content-type是对应
     3.在进行SpringBoot应用程序测试时，引入的JUnit是org.junit.jupiter.api.Test
     4.运行程序时，一定要确保XxxxMapper.xml文件 被自动放到的target目录的classes指定目录
     */

    //添加方法/接口
    //这里应该如何提交数据？
    @PostMapping("/member/save")
    public Result save(@RequestBody Member member) {
        log.info("member-service-provider member{}", member);
        int affected = memberService.save(member);
        if (affected > 0) {//说明添加成功了
            return Result.success("添加会员成功_member-service-provider-10002", affected);
        } else {
            return Result.error("401", "添加会员失败了");
        }
    }

    //查询的方法/接口
    //这里使用url的占位符+@PathVariable
    @GetMapping("/member/get/{id}")
    public Result getMemberById(@PathVariable("id") Long id) {
        Member member = memberService.queryMemberById(id);
        //使用Result把查询到的结果返回
        if (member != null) {
            return Result.success("查询会员成功_member-service-provider-10002", member);
        } else {
            return Result.error("402", "ID=" + id + "不存在");
        }
    }

}

package com.gaoxi.springcloud.controller;

import com.gaoxi.springcloud.entity.Member;
import com.gaoxi.springcloud.entity.Result;
import com.gaoxi.springcloud.service.MemberService;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.util.Date;
import java.util.concurrent.TimeUnit;

@Slf4j
@RestController
public class MemberController {
    private static final Logger log = LoggerFactory.getLogger(MemberController.class);
    //装配MemberService
    @Resource
    private MemberService memberService;

    //限流规则时在/t1上，如果关联的/t2的QPS达到1，则/t1限流
    @GetMapping("/t1")
    public Result t1() {
        return Result.success("t1()执行...");
    }

    @GetMapping("/t2")
    public Result t2() {
        return Result.success("t2()执行...");
    }

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
            return Result.success("添加会员成功_member-service-nacos-provider-10004", affected);
        } else {
            return Result.error("401", "添加会员失败了");
        }
    }

    //查询的方法/接口
    //这里使用url的占位符+@PathVariable
    //使用资源清洗的方式，以便Sentinel以/member/get的方式监控该方法的QPS
    @GetMapping("/member/get/{id}")
    public Result getMemberById(@PathVariable("id") Long id, HttpServletRequest request) {
    //修改方法参数的传递方式，以便Sentinel以/member/get的方式监控该方法的QPS，改为通过?传递参数的方式
    //访问方式修改为了 http://localhost:10004/member/get/?id=1
//    @GetMapping(value = "/member/get", params = "id")
//    public Result getMemberById(Long id) {
        //添加HttpServletRequest，验证filter
        String color = request.getParameter("color");
        String address = request.getParameter("address");
        //模拟超时，休眠5s
//        try {
//            TimeUnit.MILLISECONDS.sleep(5000);
//        } catch (InterruptedException e) {
//            throw new RuntimeException(e);
//        }
        //让线程休眠1s，模拟执行时间，以验证Sentinel以线程数来进行流控
        //重启后需要重新在Sentinel Dashboard中再次添加流控规则
//        try {
//            TimeUnit.MILLISECONDS.sleep(1000);
//        } catch (InterruptedException e) {
//            throw new RuntimeException(e);
//        }
//        System.out.println("enter getMemberById...当前线程id=" + Thread.currentThread().getId() + "时间=" + new Date());
        Member member = memberService.queryMemberById(id);
        //使用Result把查询到的结果返回
        if (member != null) {
            //return Result.success("查询会员成功_member-service-provider-10000" + " " + color + " " + address, member);
            return Result.success("查询会员成功_member-service-nacos-provider-10004", member);
        } else {
            return Result.error("402", "ID=" + id + "不存在");
        }
    }

}

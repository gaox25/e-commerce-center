package com.gaoxi.springcloud.controller;

import com.alibaba.csp.sentinel.annotation.SentinelResource;
import com.alibaba.csp.sentinel.slots.block.BlockException;
import com.gaoxi.springcloud.entity.Member;
import com.gaoxi.springcloud.entity.Result;
import com.gaoxi.springcloud.handler.CustomGlobalBlockHandler;
import com.gaoxi.springcloud.handler.CustomGlobalFallbackHandler;
import com.gaoxi.springcloud.service.MemberService;
import com.sun.deploy.security.BlockedException;
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

    //这里使用全局限流处理类，显示限流信息
    /*
     value = "t6" 表示Sentinel限流资源的名称
     blockHandlerClass = CustomGlobalBlockHandler.class 全局限流处理类
     blockHandler = "handlerMethod1" 指定使用全局限流处理类的哪个方法来处理限流信息
     fallbackClass = CustomGlobalFallbackHandler.class 全局fallback处理类，来处理java异常或业务异常
     fallback = "fallbackHandlerMethod1" 指定使用全局fallback处理类的哪个方法来处理java异常或业务异常
     exceptionsToIgnore = {RuntimeException.class} 表示如果t6抛出了这样一个异常，不再使用全局fallback处理类处理，而是使用系统默认方式处理
     */
    @GetMapping("/t6")
    @SentinelResource(value = "t6",
                      blockHandlerClass = CustomGlobalBlockHandler.class,
                      blockHandler = "handlerMethod1",
                      fallbackClass = CustomGlobalFallbackHandler.class,
                      fallback = "fallbackHandlerMethod1",
                      //exceptionsToIgnore = {RuntimeException.class})
                      exceptionsToIgnore = {NullPointerException.class})
    public Result t6() {
        //假定：当访问t6资源次数为5的倍数时，就出现java异常
        if (++num % 5 == 0) {
            //throw new RuntimeException("num的值异常 num = " + num);
            throw new NullPointerException("空指针异常 num = " + num);
        }
        //当访问t6资源次数为6的倍数时，就抛出一个Runtime异常
        if (num % 6 == 0) {
            throw new RuntimeException("RuntimeException num = " + num);
        }

        log.info("执行t6() 线程id = {}", Thread.currentThread().getId());
        return Result.success("200", "t6()执行OK~~");
    }

    //定义一个执行计数器
    private static int num = 0;

    //限流规则时在/t1上，如果关联的/t2的QPS达到1，则/t1限流
    @GetMapping("/t1")
    public Result t1() {
        return Result.success("t1()执行...");
    }

    @GetMapping("/t2")
    public Result t2() {
        //让线程休眠1s，模拟执行时间为1s -> 当多少个请求就会造成超时呢？超过10个就会
        //验证流量控制-排队规则
        try {
            TimeUnit.MILLISECONDS.sleep(1000);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
        //输出线程的信息
        log.info("执行t2() 线程id={}", Thread.currentThread().getId());
        return Result.success("t2()执行...");
    }

    @GetMapping("/t3")
    public Result t3() {
        //让线程休眠300ms，模拟执行时间大于200ms，以测试慢调用比例熔断机制
        try {
            TimeUnit.MILLISECONDS.sleep(300);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
        return Result.success("t3()执行...");
    }

    //验证单位时间内的请求数量超过限定请求数量，同时异常比例触发熔断机制
    @GetMapping("/t4")
    public Result t4() {
        //设计异常比例达到50% > 20%
        if (++num % 2 == 0) {
            //制造一个异常
            System.out.println(3 / 0);
        }
        log.info("熔断降级测试[异常比例] 执行t4() 线程id = {}", Thread.currentThread().getId());
        return Result.success("t4()执行...");
    }

    //设计一个测试案例，满足异常数的阈值，从而触发熔断机制
    //验证单位时间内的请求数量超过限定请求数量，且异常请求数量大于阈值触发熔断机制
    @GetMapping("/t5")
    public Result t5() {
        //设计出现10次异常，这里需要设计大于6，需要留出几次做测试和加入簇点链路
        if (++num <= 6) {
            //制造异常
            System.out.println(3 / 0);
        }
        log.info("熔断降级测试[异常数] 执行t5() 线程id = {}", Thread.currentThread().getId());
        return Result.success("t5()执行...");
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

    //完成对热点key限流的测试
    /*
     分析：
     1.@SentinelResource用来指定Sentinel限流资源的
     2.value="news" 表示sentinel限流资源的名称，由程序员指定
     3.blockHandler = "newsBlockHandler" 当出现限流时，由这个方法来进行处理
     */
    @GetMapping("/news")
    @SentinelResource(value = "news", blockHandler = "newsBlockHandler") //Sentinel限流资源
    public Result queryNews(@RequestParam(value="id",required = false) String id,
                            @RequestParam(value="type",required = false) String type) {
        //在实际开发中，新闻应该是到DB或者缓存获取，这里模拟一下就行了
        log.info("到DB查询新闻");
        return Result.success("返回新闻 id = " + id + " 新闻，from DB");
    }

    //热点key限制异常处理方法
    //传入的第三个参数需要时BlockException，不是BlockedException
    public Result newsBlockHandler(String id, String type, BlockException blockException) {
        return Result.success("查询id = " + id + " 新闻，触发了热点key限流保护，sorry....");
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

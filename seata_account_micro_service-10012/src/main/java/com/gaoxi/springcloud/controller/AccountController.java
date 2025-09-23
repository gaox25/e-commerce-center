package com.gaoxi.springcloud.controller;

import com.gaoxi.springcloud.entity.Result;
import com.gaoxi.springcloud.service.AccountService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.concurrent.TimeUnit;

@RestController
public class AccountController {
    @Resource
    private AccountService accountService;

    @PostMapping("/account/reduce")
    public Result result(@RequestParam("userId") Long userId, @RequestParam("money") Integer money) {

        //模拟异常，超时
        //OpenFeign接口调用默认超时时间为1s
//        try {
//            TimeUnit.SECONDS.sleep(12);
//        } catch (InterruptedException e) {
//            throw new RuntimeException(e);
//        }

        accountService.reduce(userId, money);
        return Result.success("200", "扣减账户余额OK");
    }
}

package com.gaoxi.springcloud.controller;

import com.gaoxi.springcloud.entity.Result;
import com.gaoxi.springcloud.service.AccountService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

@RestController
public class AccountController {
    @Resource
    private AccountService accountService;

    @PostMapping("/account/reduce")
    public Result result(@RequestParam("userId") Long userId, @RequestParam("money") Integer money) {
        accountService.reduce(userId, money);
        return Result.success("200", "扣减账户余额OK");
    }
}

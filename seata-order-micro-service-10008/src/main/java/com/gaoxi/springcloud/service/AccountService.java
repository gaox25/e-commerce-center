package com.gaoxi.springcloud.service;

import com.gaoxi.springcloud.entity.Result;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

@FeignClient(value = "seata-account-micro-service")
public interface AccountService {

    /*
     1.调用的方式是POST
     2.远程调用的地址为：http://seata-account-micro-service/account/reduce
     3.OpenFeign 是通过接口方式调用的
     */
    //扣减账户余额
    @PostMapping("/account/reduce")
    public Result reduce(@RequestParam("userId") Long userId, @RequestParam("money") Integer money);

}

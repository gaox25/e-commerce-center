package com.gaoxi.springcloud.service;

import com.gaoxi.springcloud.entity.Result;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

@FeignClient(value = "seata-storage-micro-service")
public interface StorageService {

    /*
     1.调用的方式是POST
     2.远程调用的地址为：http://seata-storage-micro-service/storage/reduce
     3.OpenFeign 是通过接口方式调用的
     */
    //扣减库存
    @PostMapping("/storage/reduce")
    public Result reduce(@RequestParam("productId") Long productId,@RequestParam("nums") Integer nums);

}

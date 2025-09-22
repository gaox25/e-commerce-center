package com.gaoxi.springcloud.controller;

import com.gaoxi.springcloud.entity.Result;
import com.gaoxi.springcloud.service.StorageService;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

@RestController
public class StorageController {
    @Resource
    private StorageService storageService;

    //扣减库存
    @PostMapping("/storage/reduce")
    public Result reduce(@RequestParam("productId") Long productId, @RequestParam("nums") Integer nums) {
        storageService.reduce(productId, nums);
        return Result.success("扣减库存成功ok", null);
    }
}

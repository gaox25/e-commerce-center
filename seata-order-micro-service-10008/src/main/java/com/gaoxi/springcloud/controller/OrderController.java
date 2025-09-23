package com.gaoxi.springcloud.controller;

import com.gaoxi.springcloud.entity.Order;
import com.gaoxi.springcloud.entity.Result;
import com.gaoxi.springcloud.service.OrderService;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

@RestController
public class OrderController {
    @Resource
    private OrderService orderService;

    /*
     1.这里为了测试方便，形参列表没有使用注解@RequestBody
     2.不使用注解，就使用表单发送测试数据
     */
    @PostMapping("/order/save")
    public Result save(Order order) {
        orderService.save(order);
        return Result.success("订单创建成功", null);
    }
}

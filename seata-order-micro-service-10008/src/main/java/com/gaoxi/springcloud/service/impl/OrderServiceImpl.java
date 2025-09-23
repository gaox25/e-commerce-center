package com.gaoxi.springcloud.service.impl;

import com.gaoxi.springcloud.dao.OrderDao;
import com.gaoxi.springcloud.entity.Order;
import com.gaoxi.springcloud.service.AccountService;
import com.gaoxi.springcloud.service.OrderService;
import com.gaoxi.springcloud.service.StorageService;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

@Service
@Slf4j
public class OrderServiceImpl implements OrderService {

    private static final Logger log = LoggerFactory.getLogger(OrderServiceImpl.class);
    @Resource
    private OrderDao orderDao;

    @Resource
    private StorageService storageService;

    @Resource
    private AccountService accountService;

    @Override
    public void save(Order order) {
        log.info("=== 创建订单 start ===");
        orderDao.save(order);//调用本地方法生成订单order

        log.info("=== 扣减库存 start ===");
        //远程调用storage微服务扣减库存数量
        storageService.reduce(order.getProductId(), order.getNums());
        log.info("=== 扣减库存 end ===");

        log.info("=== 扣减余额 start ===");
        //远程调用account微服务扣减用户余额
        accountService.reduce(order.getUserId(), order.getMoney());
        log.info("=== 扣减余额 end ===");

        log.info("=== 本地修改订单状态 start ===");
        //调用本地方法修改订单状态
        orderDao.update(order.getUserId(), 1);
        log.info("=== 本地修改订单状态 end ===");

        log.info("===创建订单 end===");
    }
}

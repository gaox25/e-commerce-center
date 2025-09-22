package com.gaoxi.springcloud.service;

import org.springframework.stereotype.Component;

public interface StorageService {
    //扣减库存
    void reduce(Long productId, Integer nums);
}

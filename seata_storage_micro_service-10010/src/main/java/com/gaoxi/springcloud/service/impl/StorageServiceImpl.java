package com.gaoxi.springcloud.service.impl;

import com.gaoxi.springcloud.dao.StorageDao;
import com.gaoxi.springcloud.service.StorageService;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
@Service
@Slf4j
public class StorageServiceImpl implements StorageService {

    private static final Logger log = LoggerFactory.getLogger(StorageServiceImpl.class);
    @Resource
    private StorageDao storageDao;

    @Override
    public void reduce(Long productId, Integer nums) {
        log.info("==========seata_storage_micro_service-10010 扣减库存 start==========");
        storageDao.reduce(productId, nums);
        log.info("==========seata_storage_micro_service-10010 扣减库存 end==========");
    }

}

package com.gaoxi.springcloud.service.impl;

import com.gaoxi.springcloud.dao.AccountDao;
import com.gaoxi.springcloud.service.AccountService;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

@Service
@Slf4j
public class AccountServiceImpl implements AccountService {

    private static final Logger log = LoggerFactory.getLogger(AccountServiceImpl.class);
    @Resource
    private AccountDao accountDao;

    @Override
    public void reduce(Long userId, Integer money) {
        log.info("==========seata_account_micro_service-10012 扣减余额 start==========");
        accountDao.reduce(userId, money);
        log.info("==========seata_account_micro_service-10012 扣减余额 end==========");

    }
}

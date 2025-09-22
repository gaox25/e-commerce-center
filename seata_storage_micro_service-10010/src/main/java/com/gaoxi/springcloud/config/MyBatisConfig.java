package com.gaoxi.springcloud.config;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
//常规配置，mybatis和dao关联
@Configuration
@MapperScan("com.gaoxi.springcloud.dao")
public class MyBatisConfig {

}

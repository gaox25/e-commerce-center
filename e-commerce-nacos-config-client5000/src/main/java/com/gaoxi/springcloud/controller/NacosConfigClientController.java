package com.gaoxi.springcloud.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@Slf4j
@RefreshScope //Spring Cloud原生注解，实现了配置数据的自动刷新
public class NacosConfigClientController {
    /*
     1.client即本微服务会拉取Nacos Server中Data Id为e-commerce-nacos-config-client-dev.yaml的
       config:
            ip: "122.22.22.22"
            name: "tom"
     2.@Value("${config.ip}") 会将config.ip 赋给configIp
     3.注解中的值不能随便写，必须与Nacos Server中的名称一致，否则会导致微服务无法启动
     */
    @Value("${config.ip}")
    private String configIp;

    @Value("${config.name}")
    private String configName;

    @GetMapping("/nacos/config/ip")
    public String getConfigIp() {
        return configIp;
    }

    @GetMapping("/nacos/config/name")
    public String getConfigName() {
        return configName;
    }

}

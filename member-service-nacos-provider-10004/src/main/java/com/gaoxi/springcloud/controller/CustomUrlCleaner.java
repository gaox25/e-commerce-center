package com.gaoxi.springcloud.controller;

import com.alibaba.csp.sentinel.adapter.servlet.callback.UrlCleaner;
import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Component;

@Component
public class CustomUrlCleaner implements UrlCleaner {
    @Override
    public String clean(String originUrl) {//资源清洗
        //isBlank()判断是否为空，是空返回true，否则返回false
        if (StringUtils.isBlank(originUrl)) {
            return originUrl;
        }
        //如果得到的url是以/member/get开头，进行处理
        if (originUrl.startsWith("/member/get")) {
            //1.如果请求的接口是 /member/get 开头的，比如 /member/get/1、/member/get/10
            //2.就给Sentinel返回资源名为 /member/get/*
            //3.在Sentinel中，对/member/get/*添加流控规则即可
            return "/member/get/*";
        }
        return originUrl;
    }
}

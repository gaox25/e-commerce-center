package com.gaoxi.springcloud.service;

import com.gaoxi.springcloud.entity.Result;
import org.springframework.stereotype.Component;

@Component
public class MemberFeignFallbackService implements MemberOpenFeignService{
    //这里写的是熔断后返回的信息
    @Override
    public Result getMemberById(Long id) {
        return Result.error("500", "被调用服务异常，熔断降级，快速返回结果，防止线程堆积...");
    }
}

package com.gaoxi.springcloud.handler;

import com.gaoxi.springcloud.entity.Result;

/*
 自定义的全局fallback处理类
 在该类中可以去编写处理java异常/业务异常的方法-static
 */
public class CustomGlobalFallbackHandler {
    public static Result fallbackHandlerMethod1(Throwable e) {
        return Result.error("402", "java异常 信息=" + e.getMessage());
    }

    public static Result fallbackHandlerMethod2(Throwable e) {
        return Result.error("403", "java异常 信息=" + e.getMessage());
    }
}

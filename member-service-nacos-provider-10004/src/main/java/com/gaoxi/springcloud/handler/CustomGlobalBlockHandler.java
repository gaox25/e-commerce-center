package com.gaoxi.springcloud.handler;

import com.alibaba.csp.sentinel.slots.block.BlockException;
import com.gaoxi.springcloud.entity.Result;

/*
  1.CustomGlobalBlockHandler：自定义的全局限流处理类
  2.在CustomGlobalBlockHandler类中，可以编写限流处理方法，但是要求方式是static
 */
public class CustomGlobalBlockHandler {
    public static Result handlerMethod1(BlockException blockException) {
        return Result.error("400", "客户自定义异常/限流处理方法handlerMethod1");
    }

    public static Result handlerMethod2(BlockException blockException) {
        return Result.error("401", "客户自定义异常/限流处理方法handlerMethod2");
    }
}

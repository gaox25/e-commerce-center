package com.gaoxi.springcloud.filter;

import org.springframework.cloud.gateway.filter.GatewayFilterChain;
import org.springframework.cloud.gateway.filter.GlobalFilter;
import org.springframework.core.Ordered;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

//自定义全局过滤器GlobalFilter，测试后将该过滤注释掉
//@Component
//public class CustomGateWayFilter implements GlobalFilter, Ordered {
//    //filter是最核心的方法，将我们过滤的业务写在该方法中
//    @Override
//    public Mono<Void> filter(ServerWebExchange exchange, GatewayFilterChain chain) {
//        System.out.println("----CustomGateWayFilter----");
//        //先获取到对应的参数值
//        //比如：http://localhost:20000/member/get/1?user=tom&pwd=123456&
//        //下面两个意思一样
//        //String user = exchange.getRequest().getQueryParams().get("user").get(0);
//        //验证用的地址：http://localhost:20000/member/get/1?user=tom&pwd=pwd
//        String user = exchange.getRequest().getQueryParams().getFirst("user");
//        String pwd = exchange.getRequest().getQueryParams().getFirst("pwd");
//        if (!("tom".equals(user) && "pwd".equals(pwd))) {//如果不满足条件，就返回
//            System.out.println("---- 非法用户 ----");
//            exchange.getResponse().setStatusCode(HttpStatus.NOT_ACCEPTABLE);//设置回应
//            return exchange.getResponse().setComplete();
//        }
//        //System.out.println("---- 验证通过，放行 ----");
//        return chain.filter(exchange);
//    }
//
//    //order表示过滤器执行的顺序，数字越小，优先级就越高
//    @Override
//    public int getOrder() {
//        return 0;
//    }
//}

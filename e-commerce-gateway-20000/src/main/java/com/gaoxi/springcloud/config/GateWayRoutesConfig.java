package com.gaoxi.springcloud.config;

import org.springframework.cloud.gateway.route.RouteLocator;
import org.springframework.cloud.gateway.route.builder.RouteLocatorBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

//这是一个配置类-配置路由
@Configuration
public class GateWayRoutesConfig {
    //配置注入路由
    /**
     * 在理解通过配置类注入/配置 路由，可以对照前面的application.yml来比较理解
     *   cloud:
     *     gateway:
     *       routes: #这里配置路由，可以配置多个路由，在代码中是List<RouteDefinition> routes
     *         - id: member_route01 #路由的id，程序员自己配置，要求唯一
     *           uri: http://localhost:10000
     *           predicates: #断言，可以有多种形式的断言
     *             - Path=/member/get/**
     *         - id: member_route02 #路由的id，程序员自己配置，要求唯一
     *           uri: http://localhost:10000
     *           predicates: #断言，可以有多种形式的断言
     *             - Path=/member/save
     *         - id: member_route03 #路由的id，程序员自己配置，要求唯一
     *           uri: http://www.baidu.com
     *           predicates: #断言，可以有多种形式的断言
     *             - Path=/
     */
    @Bean
    public RouteLocator myRouteLocator04(RouteLocatorBuilder routeLocatorBuilder) {
        RouteLocatorBuilder.Builder routes = routeLocatorBuilder.routes();
        /**
         * 1.下面的方法分别指定了id，uri和path
         * 2.Function<PredicateSpec, Route.AsyncBuilder> fn
         *  (1)是一个函数式接口
         *  (2)接收的类型是PredicateSpec，返回的类型是Route.AsyncBuilder
         *  (3)r -> r.path("/member/get/**").uri("http://localhost:10000")就是lambda表达式
         * 3.可以理解这是一个规定的写法
         */
        return routes.route("member_route04", r -> r.path("/member/get/**")
                            .uri("http://localhost:10000"))
                            .build();
    }

    @Bean
    public RouteLocator myRouteLocator05(RouteLocatorBuilder routeLocatorBuilder) {
        RouteLocatorBuilder.Builder routes = routeLocatorBuilder.routes();
        return routes.route("member_route05", r -> r.path("/member/save")
                        .uri("http://localhost:10000"))
                .build();
    }
}

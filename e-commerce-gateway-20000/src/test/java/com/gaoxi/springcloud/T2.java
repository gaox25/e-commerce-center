package com.gaoxi.springcloud;

import java.time.ZonedDateTime;

public class T2 {
    public static void main(String[] args) {
        //获取Gateway predicate所要求的时间格式
        ZonedDateTime now = ZonedDateTime.now();
        System.out.println(now);
    }
}

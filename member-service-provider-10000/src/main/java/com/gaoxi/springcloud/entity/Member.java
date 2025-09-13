package com.gaoxi.springcloud.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data //使用lombok的注解@Data自动生成setter、getter、toString
@NoArgsConstructor //生成一个无参构造器
@AllArgsConstructor //生成一个全残构造器
public class Member {
    private Long id;
    private String name;
    private String pwd;
    private String mobile;
    private String email;
    private Integer gender;

}

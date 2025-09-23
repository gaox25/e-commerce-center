package com.gaoxi.springcloud.dao;

import com.gaoxi.springcloud.entity.Order;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

@Mapper
public interface OrderDao {
    //新建订单
    void save(Order order);
    //修改订单状态
    void update(@Param("userId") Long userId, @Param("status") Integer status);
}

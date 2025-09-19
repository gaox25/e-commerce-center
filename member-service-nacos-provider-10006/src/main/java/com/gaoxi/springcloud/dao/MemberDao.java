package com.gaoxi.springcloud.dao;

import com.gaoxi.springcloud.entity.Member;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface MemberDao {

    //根据id返回member信息
    public Member queryMemberById(Long id);

    //添加member到表中，返回受影响的行数
    public int save(Member member);

}

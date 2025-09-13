package com.gaoxi.springcloud.service;

import com.gaoxi.springcloud.entity.Member;
import org.springframework.stereotype.Service;

public interface MemberService {

    //根据id返回member
    public Member queryMemberById(Long id);

    //添加member
    public int save(Member member);
}

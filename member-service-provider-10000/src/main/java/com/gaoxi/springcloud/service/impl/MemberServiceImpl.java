package com.gaoxi.springcloud.service.impl;

import com.gaoxi.springcloud.dao.MemberDao;
import com.gaoxi.springcloud.entity.Member;
import com.gaoxi.springcloud.service.MemberService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
@Service
public class MemberServiceImpl implements MemberService {

    //装配MemberDao
    @Resource
    private MemberDao memberDao;

    @Override
    public Member queryMemberById(Long id) {
        return memberDao.queryMemberById(id);
    }

    @Override
    public int save(Member member) {
        return memberDao.save(member);
    }
}

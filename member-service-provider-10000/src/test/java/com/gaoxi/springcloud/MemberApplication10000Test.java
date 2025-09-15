package com.gaoxi.springcloud;

import com.gaoxi.springcloud.dao.MemberDao;
import com.gaoxi.springcloud.entity.Member;
import com.gaoxi.springcloud.service.MemberService;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import javax.annotation.Resource;

@Slf4j
@SpringBootTest
public class MemberApplication10000Test {
    //装配MemberDao
    @Resource
    private MemberDao memberDao;

    //装配MemberService
    @Resource
    private MemberService memberService;

    //强调：这里的@Test注解需要引入org.junit.jupiter.api，别的会空指针异常
    @Test
    public void queryMemberById() {
        //测试MemberDao
        //Member member = memberDao.queryMemberById(1L);
        //测试MemberService
        Member member = memberService.queryMemberById(2L);
        System.out.println("member{} = " + member);
    }

    @Test
    public void save() {
        //这个地方很奇怪，使用lombok的注解@AllArgsConstructor后，会有全参的构造器，但是这里提示没有
        //实际运行也没有问题，先注掉
        //Member member = new Member(null, "牛魔王", "123", "12345667898", "nmw@sohu.com", 1);
        //Member member1 = new Member(null, "孙悟空", "123", "12345667898", "nmw@sohu.com", 1);
        //测试MemberDao
//        int save = memberDao.save(member);
        //测试MemberService
        //int save = memberService.save(member1);
        //System.out.println("save() = " + save);
    }
}

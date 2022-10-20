package com.example.datajpa.repository;

import com.example.datajpa.entity.Member;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@Transactional
class MemberRepoTest {

    @Autowired MemberRepo memberRepo;

    @Test
    public void testMember(){
        Member member = new Member();
        member.setUsername("member1");

        Member saveMember = memberRepo.save(member);

        Member findMember = memberRepo.findById(saveMember.getId()).get();

        Assertions.assertThat(findMember.getId()).isEqualTo(saveMember.getId());
        Assertions.assertThat(findMember.getUsername()).isEqualTo(saveMember.getUsername());

    }
}
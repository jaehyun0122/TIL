package com.example.datajpa.repository;

import com.example.datajpa.entity.Member;
import com.example.datajpa.entity.Team;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@Transactional
class MemberJpaRepoTest {

    @Autowired MemberJpaRepo memberJpaRepo;

    @Test
    public void testMember(){
        Team team = new Team("team1");
        Member member = new Member("member1", 20, team);


        Member saveMember = memberJpaRepo.save(member);

        Member findMember = memberJpaRepo.find(saveMember.getId());

        Assertions.assertThat(findMember.getId()).isEqualTo(saveMember.getId());
        Assertions.assertThat(findMember.getUsername()).isEqualTo(saveMember.getUsername());
    }

    @Test
    public void crud(){
        Member member1 = new Member("member1");
        Member member2 = new Member("member2");
        memberJpaRepo.save(member1);
        memberJpaRepo.save(member2);

        Member findMember1 = memberJpaRepo.findById(member1.getId()).get();
        Member findMember2 = memberJpaRepo.findById(member2.getId()).get();

        Assertions.assertThat(findMember1).isEqualTo(member1);
        Assertions.assertThat(findMember2).isEqualTo(member2);

        long countMember = memberJpaRepo.count();
        Assertions.assertThat(countMember).isEqualTo(2);

        memberJpaRepo.delete(member1);
        memberJpaRepo.delete(member2);
        long deleteCount = memberJpaRepo.count();
        Assertions.assertThat(deleteCount).isEqualTo(0);
    }

}
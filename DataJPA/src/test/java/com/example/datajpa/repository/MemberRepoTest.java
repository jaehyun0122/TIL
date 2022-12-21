package com.example.datajpa.repository;

import com.example.datajpa.entity.Member;
import com.example.datajpa.entity.Team;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@Transactional
@Rollback(value = false)
class MemberRepoTest {

    @Autowired MemberRepo memberRepo;
    @Autowired TeamRepo teamRepo;
    @Test
    public void testMember(){
        Team team = new Team("team1");
        teamRepo.save(team);
        Member member = new Member("member1", 20, team);


        Member saveMember = memberRepo.save(member);
        saveMember.setUsername("setMember");
        Member findMember = memberRepo.findByUsername("setMember");
        System.out.println(findMember.getUsername());

//        Member findMember = memberRepo.findById(saveMember.getId()).get();
//
//        Assertions.assertThat(findMember.getId()).isEqualTo(saveMember.getId());
//        Assertions.assertThat(findMember.getUsername()).isEqualTo(saveMember.getUsername());

    }
}

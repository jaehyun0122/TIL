package com.example.querydsl.repository;

import com.example.querydsl.entity.Member;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface MemberRepo extends JpaRepository<Member, Long>, MemberRepoCustom {
    List<Member> findByUsername(String username);
}

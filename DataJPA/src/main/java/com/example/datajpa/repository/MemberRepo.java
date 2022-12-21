package com.example.datajpa.repository;

import com.example.datajpa.entity.Member;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MemberRepo extends JpaRepository<Member, Long> {

    Member findByUsername(String username);
}

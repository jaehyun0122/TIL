package com.example.repository;

import com.example.jpapractice.JpaPracticeApplication;
import com.example.jpapractice.domain.User;
import com.example.jpapractice.repository.UserRepository;
import org.hibernate.criterion.Order;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.test.annotation.Rollback;

import java.time.LocalDateTime;

@SpringBootTest(classes = JpaPracticeApplication.class)
class UserRepositoryTest {

    @Autowired
    private UserRepository userRepository;

    @Test
    @Rollback(value = false)
    void crud(){
//        userRepository.delete(userRepository.findById(1L).orElse(null));
//       userRepository.deleteAllInBatch();
//        System.out.println("deleteAll : " + userRepository.deleteAll(););
//       userRepository.deleteAll();
//        Page<User> users = userRepository.findAll(PageRequest.of(1,3));
//        System.out.println("getTotalElements : "+users.getTotalElements());
//        System.out.println("getTotalPages : "+users.getTotalPages());
//        System.out.println(users.getNumber());
//        System.out.println("findByEmailAndName : "+userRepository.findByEmailAndName("test@test","test"));
//        System.out.println("findByNameOrId : "+userRepository.findByNameOrEmail("test","test3"));
//        System.out.println("findByCreatedAtEquals : "+userRepository.findByCreatedAtEquals(LocalDateTime.now()));
//        System.out.println("findByIdAfter : "+userRepository.findByIdAfter(2L));
//        System.out.println("findByIdBetween : "+userRepository.findByIdBetween(2L,3L));
//        System.out.println("findByIdGreaterThanEqualAndIdLessThanEqual : "+userRepository.findByIdGreaterThanEqualAndIdLessThanEqual(1L,3L));
//        System.out.println("findByNameStartingWith : "+userRepository.findByNameStartingWith("t"));
//        System.out.println("findByNameEndingWith : "+userRepository.findByNameEndingWith("3"));
//        System.out.println("findByNameContains : "+userRepository.findByNameContains("st"));
//        System.out.println(userRepository.findFirstByNameOrderByIdDesc("test"));
//        System.out.println(userRepository.findFirstByName("test", Sort.by(Sort.Order.desc("id"), Sort.Order.asc("email"))));;
        System.out.println(userRepository.findByName("test",PageRequest.of(0,1,Sort.by(Sort.Order.desc("id")))).getContent());
    }
    private Sort getSort(){
        return Sort.by(
            Sort.Order.asc("id"),
            Sort.Order.desc("name"),
            Sort.Order.desc("createdAt")
        );
    }
}
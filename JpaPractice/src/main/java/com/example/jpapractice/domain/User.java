package com.example.jpapractice.domain;

import com.example.jpapractice.domain.listener.Common;
import com.example.jpapractice.domain.listener.MyEntityListener;
import com.sun.istack.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Builder
@AllArgsConstructor
@NoArgsConstructor
@Entity
@EntityListeners(value = MyEntityListener.class)
@Data
@Table(name = "user")
public class User implements Common {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Enumerated(value = EnumType.STRING)
//    @Column
    private Gender gender;

    @NotNull
    private String name;

    @NotNull
    private String email;

    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;

    @OneToMany(fetch = FetchType.EAGER)
    // insertable , updatable 유저 테이블에서는 삽입, 수정하지 않도록
    @JoinColumn(name = "user_id", insertable = false, updatable = false) // 어노테이션 없으면 user_user_histories 테이블이 하나 생김
    private List<UserHistory> userHistories = new ArrayList<>(); // null exception 발생하지 않도록 기본 생성자

//    @PrePersist
//    private void prePersist(){
//        this.createdAt = LocalDateTime.now();
//    }
//
//    @PreUpdate
//    private void preUpdate(){
//        this.updatedAt = LocalDateTime.now();
//    }
}

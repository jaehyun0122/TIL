package com.example.jpapractice.domain;

import com.example.jpapractice.domain.listener.Common;
import com.example.jpapractice.domain.listener.MyEntityListener;
import com.sun.istack.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

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

    // insertable , updatable 유저 테이블에서는 삽입, 수정하지 않도록
    @OneToMany(fetch = FetchType.EAGER)
    @JoinColumn(name = "user_id", insertable = false, updatable = false) // 어노테이션 없으면 user_user_histories 테이블이 하나 생김
    private List<UserHistory> userHistories = new ArrayList<>(); // null exception 발생하지 않도록 기본 생성자

    @OneToMany
    @JoinColumn(name = "user_id")
    @ToString.Exclude
    private List<Review> reviews = new ArrayList<>();

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

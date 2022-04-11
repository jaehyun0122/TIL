package com.example.jpapractice.domain;

import com.sun.istack.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.context.event.EventListener;

import javax.persistence.*;
import java.time.LocalDateTime;

@Builder
@AllArgsConstructor
@NoArgsConstructor
@Entity
@EntityListeners(value = MyEntityListener.class)
@Data
@Table(name = "user")
public class User implements Common{
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

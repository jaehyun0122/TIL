package com.example.jpapractice.domain;

import com.example.jpapractice.domain.listener.Common;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@NoArgsConstructor
@Data
public class UserHistory implements Common {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "user_id", insertable = false, updatable = false)
    private Long userId;

    private String name;

    private String email;

    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;

    @ManyToOne
    @ToString.Exclude
    private User user;
}

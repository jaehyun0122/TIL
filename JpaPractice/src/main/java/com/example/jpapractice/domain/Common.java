package com.example.jpapractice.domain;

import java.time.LocalDateTime;

public interface Common {
    LocalDateTime getCreatedAt();
    LocalDateTime getUpdatedAt();

    void setCreatedAt(LocalDateTime createdAt);
    void setUpdatedAt(LocalDateTime updateedAt);
}

package com.example.jpapractice.domain.listener;

import java.time.LocalDateTime;

public interface Common {
    LocalDateTime getCreatedAt();
    LocalDateTime getUpdatedAt();

    void setCreatedAt(LocalDateTime createdAt);
    void setUpdatedAt(LocalDateTime updatedAt);
}

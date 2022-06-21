package com.example.jpapractice.domain.listener;

import com.example.jpapractice.domain.User;
import com.example.jpapractice.domain.UserHistory;
import com.example.jpapractice.repository.UserHistoryRepository;
import org.springframework.beans.factory.annotation.Autowired;

import javax.persistence.PostPersist;
import javax.persistence.PostUpdate;
import javax.persistence.PrePersist;
import javax.persistence.PreUpdate;
import java.time.LocalDateTime;


public class MyEntityListener{

    @PrePersist
    public void prePersist(Object o) {
        if (o instanceof Common) {
            ((Common) o).setUpdatedAt(LocalDateTime.now());
            ((Common) o).setCreatedAt(LocalDateTime.now());
        }
    }

    @PreUpdate
    public void preUpdate(Object o) {
        if (o instanceof Common) {
            ((Common) o).setUpdatedAt(LocalDateTime.now());
        }
    }
}

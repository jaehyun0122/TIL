package com.example.jpapractice.domain;

import javax.persistence.PrePersist;
import java.time.LocalDateTime;

public class MyEntityListener {
    @PrePersist
    public void prePersist(Object o){
        if(o instanceof Common){
            ((Common) o).setCreatedAt(LocalDateTime.now());
        }
    }
}

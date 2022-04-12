package com.example.jpapractice.domain;

import javax.persistence.PrePersist;
import javax.persistence.PreUpdate;
import java.time.LocalDateTime;


public class MyEntityListener{


    @PrePersist
    public void prePersist(Object o){
        if(o instanceof Common){
            ((Common) o).setCreatedAt(LocalDateTime.now());
        }
    }

    @PreUpdate
    public void preUpdate(Object o){
        if(o instanceof Common){
            ((Common) o).setUpdatedAt(LocalDateTime.now());
        }
    }
}

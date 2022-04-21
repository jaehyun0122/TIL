package com.example.jpapractice.domain;

import com.example.jpapractice.repository.UserHistoryRepository;
import org.springframework.beans.factory.annotation.Autowired;

import javax.persistence.PostPersist;
import javax.persistence.PostUpdate;
import javax.persistence.PrePersist;
import javax.persistence.PreUpdate;
import java.time.LocalDateTime;


public class MyEntityListener{

    @Autowired
    private UserHistoryRepository userHistoryRepository;
    @PostPersist
    @PostUpdate
    public void prePersistAndPreUpdate(Object o){
        if(o instanceof Common){
            ((Common) o).setUpdatedAt(LocalDateTime.now());
            if(((Common) o).getCreatedAt() == null){
                ((Common) o).setCreatedAt(LocalDateTime.now());
            }

            User user = (User) o;
            System.out.println(user);
            UserHistory userHistory = new UserHistory();
            userHistory.setUserId(user.getId());
            userHistory.setName(user.getName());
            userHistory.setEmail(user.getEmail());
            System.out.println("userHistory" + userHistory);
            userHistoryRepository.save(userHistory);
        }
    }
}

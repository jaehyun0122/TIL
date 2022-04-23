package com.example.jpapractice.domain.listener;

import com.example.jpapractice.domain.User;
import com.example.jpapractice.domain.UserHistory;
import com.example.jpapractice.repository.UserHistoryRepository;
import org.springframework.beans.factory.annotation.Autowired;

import javax.persistence.PostPersist;
import javax.persistence.PostUpdate;

public class UserEntityListener {
    @Autowired
    private UserHistoryRepository userHistoryRepository;

    @PostPersist
    @PostUpdate
    public void prePersistAndPreUpdate(Object o) {

        User user = (User) o;

        UserHistory userHistory = new UserHistory();
        userHistory.setName(user.getName());
        userHistory.setEmail(user.getEmail());
        userHistory.setUser(user);

        userHistoryRepository.save(userHistory);
    }
}
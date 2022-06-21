package com.example.jpapractice.controller;

import com.example.jpapractice.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class Controller {

    @Autowired
    private UserRepository userRepository;

    @GetMapping("")
    public String getAny(){
        return null;
    }
}

package com.example.dnsapp;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@SpringBootApplication
public class DnsAppApplication {

    @RestController
    class HelloController{
        @GetMapping("/")
        public String Hello(){
            return "Hello DNS";
        }
    }
    public static void main(String[] args) {
        SpringApplication.run(DnsAppApplication.class, args);
    }

}

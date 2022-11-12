package com.example.querydsl.entity;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import java.util.ArrayList;

@Entity
@Getter @Setter
public class Hello {

    @Id @GeneratedValue
    private Long Id;

}

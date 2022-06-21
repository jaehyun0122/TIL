package com.example.jpapractice.domain;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@Data
@NoArgsConstructor
public class BookReviewInfo {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

//    private Long BookId;

    @OneToOne(optional = false)
    private Book book;

    private float averageReviewScore;

    private int reviewCount;
}

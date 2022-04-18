package com.example.jpapractice.repository;

import com.example.jpapractice.domain.BookReviewInfo;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BookReviewRepository extends JpaRepository<BookReviewInfo, Long> {
}

package com.example.jpapractice.repository;

import com.example.jpapractice.domain.Book;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BookRespository extends JpaRepository<Book, Long> {

}

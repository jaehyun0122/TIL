package com.example.jpapractice.domain;

import com.example.jpapractice.JpaPracticeApplication;
import com.example.jpapractice.repository.BookRespository;
import com.example.jpapractice.repository.BookReviewRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest(classes = JpaPracticeApplication.class)
class BookTest {

    @Autowired
    private BookRespository bookRespository;
    @Autowired
    private BookReviewRepository bookReviewRepository;

    @Test
    void bookTest(){
        Book book = new Book();
        book.setName("BookName");
        book.setAuthorId(1L);
        book.setPublisherId(1L);

        bookRespository.save(book);

        System.out.println("Book>>>>"+bookRespository.findAll());

        BookReviewInfo bookReviewInfo = new BookReviewInfo();
        bookReviewInfo.setBook(book);
        bookReviewInfo.setReviewCount(1);
        bookReviewInfo.setAverageReviewScore(3);

        bookReviewRepository.save(bookReviewInfo);

        System.out.println("BookReview>>>"+bookReviewRepository.findAll());

        Book result = bookReviewRepository
                        .findById(1L)
                        .orElseThrow(RuntimeException::new)
                        .getBook();

        System.out.println("Result>>>"+result);
    }
}
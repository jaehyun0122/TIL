package com.example.jpapractice.domain;

import com.example.jpapractice.JpaPracticeApplication;
import com.example.jpapractice.repository.*;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import javax.transaction.Transactional;

@SpringBootTest(classes = JpaPracticeApplication.class)
class BookTest {

    @Autowired
    private BookRespository bookRespository;
    @Autowired
    private BookReviewRepository bookReviewRepository;
    @Autowired
    private PublisherRepository publisherRepository;
    @Autowired
    private ReviewRepository reviewRepository;
    @Autowired
    private UserRepository userRepository;

    @Test
    void bookTest(){
        Book book = new Book();
        book.setName("BookName");
        book.setAuthorId(1L);

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

    @Test
    @Transactional
    void bookRelation(){
        insertBookAndReview();

        User user = userRepository.findByEmail("test@naver.com");

        System.out.println("Review : "+user.getReviews());
        System.out.println("Book : "+user.getReviews().get(0).getBook());
        System.out.println("Publisher : "+user.getReviews().get(0).getBook().getPublisher());
    }
    private void insertBookAndReview(){
        insertReview(insertUser(), insertBook(insertPublisher()));
    }

    private User insertUser(){
        return userRepository.findByEmail("test@naver.com");
    }

    private Book insertBook(Publisher publisher){
        Book book = new Book();
        book.setName("jap practice");
        book.setPublisher(publisher);

        return bookRespository.save(book);
    }

    private void insertReview(User user, Book book){
        Review review = new Review();
        review.setTitle("자바 마스터 하기");
        review.setContent("유용하네요");
        review.setScore(5.0f);
        review.setUser(user);
        review.setBook(book);

        reviewRepository.save(review);
    }

    private Publisher insertPublisher(){
        Publisher publisher = new Publisher();
        publisher.setName("출판사 이름");

        return publisherRepository.save(publisher);
    }
}
package com.example.jpapractice.repository;

import com.example.jpapractice.JpaPracticeApplication;
import com.example.jpapractice.domain.Author;
import com.example.jpapractice.domain.Book;
import org.assertj.core.util.Lists;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import javax.transaction.Transactional;

import static org.junit.jupiter.api.Assertions.*;
@SpringBootTest(classes = JpaPracticeApplication.class)
class AuthorRepositoryTest {

    @Autowired
    private AuthorRepository authorRepository;
    @Autowired
    private BookRespository bookRespository;

    @Test
    @Transactional
    void manyToManyTest(){
        Book book1 = insertBook("1번책");
        Book book2 = insertBook("2번책");

        Book book3 = insertBook("1프로그래밍");
        Book book4 = insertBook("2프로그래밍");

        Author author1 = insertAuthor("Rooney");
        Author author2 = insertAuthor("Gerrard");

        book1.addAuthor(author1);
        book2.addAuthor(author2);
        book3.addAuthor(author1, author2);
        book4.addAuthor(author1, author2);

        author1.addBook(book1, book3, book4);
        author2.addBook(book2, book3, book4);

        bookRespository.saveAll(Lists.newArrayList(book1,book2,book3,book4));
        authorRepository.saveAll(Lists.newArrayList(author1,author2));

        System.out.println("authors ~ book : "+bookRespository.findAll().get(2).getAuthors()); // 1번프로그래밍의 저자들
        System.out.println("book ~ authors : "+authorRepository.findAll().get(1).getBooks()); // Gerrard가 쓴 책들
    }

    private Book insertBook(String name){
        Book book = new Book();
        book.setName(name);

        return bookRespository.save(book);
    }

    private Author insertAuthor(String name){
        Author author = new Author();
        author.setName(name);

        return authorRepository.save(author);
    }
}
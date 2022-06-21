package com.example.jpapractice.domain;

import com.example.jpapractice.domain.listener.Common;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

@Entity
@NoArgsConstructor
@Data
public class Author implements Common {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;
    private String country;

    @ManyToMany
    @ToString.Exclude
    private List<Book> books = new ArrayList<>();

    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;

    public void addBook(Book... book){ // ...은 배열로 받겠다는 의미
//        Collections.addAll(this.books, book);
        if(book != null){
            for(Book book1 : book){
                this.books.add(book1);
            }
        }
    }
}

# JPA

> ### ORM 
>
> 데이터베이스와 객체를 연결해주는 역할

## 1. Jpa Repository Interface

### 1)findeAll(Sort.by(Direction.DESC, "name"));

=> 이름 순으로 내림차순 정렬



### 2)findAllById(Lists.newArrayList(1L,3L, 4L));

=> id가 1,3,4 인 데이터만 가져온다



### 3) delete query의 차이점 (직접 확인해보기)

delete :  

```
Hibernate: select user0_.id as id1_0_0_, user0_.created_at as created_2_0_0_, user0_.email as email3_0_0_, user0_.name as name4_0_0_, user0_.updated_at as updated_5_0_0_ from user user0_ where user0_.id=1
Hibernate: delete from user where id=1
```



deleteAll : findAll()을 먼저 수행하고 delete를 수행한다.

```
Hibernate: select user0_.id as id1_0_, user0_.created_at as created_2_0_, user0_.email as email3_0_, user0_.name as name4_0_, user0_.updated_at as updated_5_0_ from user user0_
Hibernate: delete from user where id=2
Hibernate: delete from user where id=3
Hibernate: delete from user where id=4
```



deleteAllInBatch() : delete 1번 실행. 쿼리 한번으로 모든 것을 지운다.

```
delete from user
```





### 4) Page타입 => 간단하게 페이징처리가 가능하다.

```
        Page<User> users = userRepository.findAll(PageRequest.of(1,3));
        System.out.println("getTotalElements : "+users.getTotalElements());
        System.out.println("getTotalPages : "+users.getTotalPages());
```

결과

```
getTotalElements : 4
getTotalPages : 2
```





## 2. queryMethod 활용해보기

https://docs.spring.io/spring-data/jpa/docs/current/reference/html/#jpa.query-methods

### 1) jpa application.yml setting 

```
spring:
  jpa:
      show-sql: true // sql문 확인
      properties:
        hibernate:
          format_sql: true // 보기 편한 sql문으로 변환

logging:
  level:
    org:
      hibernate:
        type: trace // 파라미터 값 확인
```



> findBy~~ 이런 문장을 많이쓴다.
>
> 내가 느끼기론 
>
> find selelct
>
> by   where
>
> id     id
>
> 이런 방식

여러가지 방식으로 디비 조회가 가능하다.



### 2) 정렬

```
List<User> findFirstByNameOrderByIdDesc(String name);
```

```
userRepository.findFirstByNameOrderByIdDesc("test"));
```

결과

```
Hibernate: 
    select
        user0_.id as id1_0_,
        user0_.created_at as created_2_0_,
        user0_.email as email3_0_,
        user0_.name as name4_0_,
        user0_.updated_at as updated_5_0_ 
    from
        user user0_ 
    where
        user0_.name=? 
    order by
        user0_.id desc limit ?
        
 [User(id=1, name=test, email=test@test, createdAt=2022-03-03T23:08:58, updatedAt=2022-03-03T23:08:58)]
```

#### 여러 조건으로 정렬을 하게되면 함수명이 길어져 가독성이 떨어질 수 있다.

```
List<User> findFirstByName(String name, Sort sort);
```

```
System.out.println(userRepository.findFirstByName("test", Sort.by(Sort.Order.desc("id"), Sort.Order.asc("email"))));;
```

결과

```
Hibernate: 
    select
        user0_.id as id1_0_,
        user0_.created_at as created_2_0_,
        user0_.email as email3_0_,
        user0_.name as name4_0_,
        user0_.updated_at as updated_5_0_ 
    from
        user user0_ 
    where
        user0_.name=? 
    order by
        user0_.id desc,
        user0_.email asc limit ?
        
[User(id=1, name=test, email=test@test, createdAt=2022-03-03T23:08:58, updatedAt=2022-03-03T23:08:58)]
```

> Sort.Order를 통해 인자값을 넘겨주면 여러가지 조건으로 정렬을 할 수 있다.
>
> 또는 동일한 조건으로 여러 코드를 수행하면 Sort 클래스를 만들어 줄 수도 있다

```
private Sort getSort(){
        return Sort.by(
            Sort.Order.asc("id"),
            Sort.Order.desc("name"),
            Sort.Order.desc("createdAt")
        );
    }
```

### 3) 페이징 처리

```
    // Page : 응답값,  Pageable : 요청값
    Page<User> findByName(String name, Pageable pageable);
```

```
System.out.println(userRepository.findByName("test",PageRequest.of(0,1,Sort.by(Sort.Order.desc("id")))).getContent());
```

결과

```
Hibernate: 
    select
        user0_.id as id1_0_,
        user0_.created_at as created_2_0_,
        user0_.email as email3_0_,
        user0_.name as name4_0_,
        user0_.updated_at as updated_5_0_ 
    from
        user user0_ 
    where
        user0_.name=? 
    order by
        user0_.id desc limit ?
        
        
        
        Hibernate: 
    select
        count(user0_.id) as col_0_0_ 
    from
        user user0_ 
    where
        user0_.name=?
        
        
    [User(id=1, name=test, email=test@test, createdAt=2022-03-03T23:08:58, updatedAt=2022-03-03T23:08:58)]
```

> 사이즈에 맞게 결과를 가져온다.



### 4) Entity 속성

1. @Column

- @Column(updatable = false)
- @Conlumn(insertable = false)

insert, update시 변경되지 않는다.

- nullable : default = true



2. native qeury

@Query(vale="", native = true)

value의 쿼리문이 실행된다.



3. @GeneratedValue

TABLE : DB종류에 상관없이 id를 관리하는 별도의 테이블 만들어서 제공

SEQUENCE 

IDENTITY 

AUTO : default값



4. @Table

기본이름은 class이름으로 지정된다

@Table(name = "user_table") => table이름을 지정해 줄 수 있다.

```
@Table(name = "user", indexes = {@Index(columnList = "name")}, uniqueConstraints = {@UniqueConstraint(columnNames = "email")})
```

위와 같이 Index나 제약조건을 걸어 줄  수도 있다.

=> DB에 Index나 제약사항이 없는데 jpa에서 지정해주면 동작하지 않는다. 그래서 Index나 제약사항같은 경우는 DB에 맞기는 부분이 많다고 한다.



5. @Transient

영속성 처리에서 제외. DB에 반영하지 않고 객체에서 따로 사용할 경우.



6. @Enumerated

enum을 사용하는 컬럼의 경우 반드시 @Enumerated(value = EnumType.STRING)을 해줘야한다.

=> 안해주면 default가 ORDINAL이기 때문에 순서값이 저장되게 된다.



### 3. Entity Listener

1. PrePersist : insert 동작전
2. PreUpdate : update 전
3. PreRemove : delete 전
4. PostPersist : insert 후
5. PostUpdate : update 후
6. PostRemove : delete 후
7. PostLoad : select 후

=> PrePersist, PreUpdate를 많이 쓴다. 

예를 들어 유저 정보는 중요한 정보이다. 유저 정보의 생성시간이나 업데이트시간들은 반복되는 작업이기 때문에 객체 생성시 지정해 주지 않고

1. PrePersist나 PreUpdate로 설정하면 개발자의 실수도 줄일 수 있다.

```
    @prePersist
    private void PrePersist(){
        this.createdAt = LocalDateTime.now();
    }

    @preUpdate
    private void PreUpdate(){
        this.updatedAt = LocalDateTime.now();
    }
```



2. class를 만들어 Listener설정

```
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Entity
@EntityListeners(value = MyEntityListener.class) => EntityListeners 어노테이션
@Data
@Table(name = "user")
public class User implements Common{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Enumerated(value = EnumType.STRING)
    private Gender gender;

    @NotNull
    private String name;

    @NotNull
    private String email;

    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
}

```

```
package com.example.jpapractice.domain;

import java.time.LocalDateTime;

public interface Common {
    LocalDateTime getCreatedAt();
    LocalDateTime getUpdatedAt();

    void setCreatedAt(LocalDateTime createdAt);
    void setUpdatedAt(LocalDateTime updateedAt);
}

```

```
package com.example.jpapractice.domain;

import javax.persistence.PrePersist;
import java.time.LocalDateTime;

public class MyEntityListener {
    @PrePersist
    public void prePersist(Object o){
        if(o instanceof Common){
            ((Common) o).setCreatedAt(LocalDateTime.now());
        }
    }
}

```



### 4. 연관관계 및 ERD

ERD 설계 시 draw.io활용

https://www.draw.io

![jpaERd](https://github.com/jaehyun0122/Jpa_practice/blob/master/readmeAsset/jpaERd.PNG)

1. 1:1 연관관계


1)

### TestCode

```
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
        bookReviewInfo.setBookId(1L);
        bookReviewInfo.setReviewCount(1);
        bookReviewInfo.setAverageReviewScore(3);

        bookReviewRepository.save(bookReviewInfo);

        System.out.println("BookReview>>>"+bookReviewRepository.findAll());

        Book result = bookRespository.findById(
                bookReviewRepository
                        .findById(1L)
                        .orElseThrow(RuntimeException::new)
                        .getBookId()
        ).orElseThrow(RuntimeException::new);

        System.out.println("Result>>>"+result);
    }
}
```



### Entity

```
package com.example.jpapractice.domain;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@NoArgsConstructor
@Data
@ToString(callSuper = true)
public class Book implements Common{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;

    private String category;

    private Long authorId;

    private Long publisherId;

    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;

}

```

```
package com.example.jpapractice.domain;

import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@Data
@NoArgsConstructor
public class BookReviewInfo {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Long BookId;

    private float averageReviewScore;

    private int reviewCount;
}

```



### 결과

```
Book>>>>[Book(super=com.example.jpapractice.domain.Book@ad4d0dfb, id=1, name=BookName, category=null, authorId=1, publisherId=1, createdAt=null, updatedAt=null)]
```

```
BookReview>>>[BookReviewInfo(id=1, BookId=1, averageReviewScore=3.0, reviewCount=1)]
```

```
Result>>>Book(super=com.example.jpapractice.domain.Book@ad4d0dfb, id=1, name=BookName, category=null, authorId=1, publisherId=1, createdAt=null, updatedAt=null)
```



2)

## 직접 참조

### BookReveiwEntity

private Long BookId => 

@OneToOne(optional = false)

private Book book;

####  OneToOne의 속성에 optional=false로 하면 반드시 존재하는 값을 가져온다

#### optional은 default가 ture이기 때문에 false로 하면 밑의 DML부분에서 inner join을 하게 되고 true이면 left outter join을 하게 된다.

```
package com.example.jpapractice.domain;

import lombok.Data;
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

```

#### DDL을 살펴보면

```
    create table book_review_info (
       id bigint not null auto_increment,
        average_review_score float not null,
        review_count integer not null,
        book_id bigint not null,
        primary key (id)
    )
```

book_id 컬럼이 들어가 있는 것을 볼 수 있다.

#### DML select부분을 살펴보면

```
    select
        bookreview0_.id as id1_1_0_,
        bookreview0_.average_review_score as average_2_1_0_,
        bookreview0_.book_id as book_id4_1_0_,
        bookreview0_.review_count as review_c3_1_0_,
        book1_.id as id1_0_1_,
        book1_.author_id as author_i2_0_1_,
        book1_.category as category3_0_1_,
        book1_.created_at as created_4_0_1_,
        book1_.name as name5_0_1_,
        book1_.publisher_id as publishe6_0_1_,
        book1_.updated_at as updated_7_0_1_ 
    from
        book_review_info bookreview0_ 
    inner join
        book book1_ 
            on bookreview0_.book_id=book1_.id 
    where
        bookreview0_.id=?
```

join을 통해 bookreview, book테이블의 book_id가 같을 것을 찾아온다



#### result

```
Result>>>Book(super=com.example.jpapractice.domain.Book@ad4d0dfb, id=1, name=BookName, category=null, authorId=1, publisherId=1, createdAt=null, updatedAt=null)
```



3) OneToOne 속성

1. mappedBy

연관 키를 가지지 않게 된다. 객체간의 양방향 연관관계는 엄밀히 말하면 없다.

그래서 연관관계의 주인(주인은 mappedBy를 사용하지 않은 엔터티가 된다.)을 설정해 하나의 컬럼으로 조인을 통해 데이터를 가져올 수 있다.

book엔터티에는 book_review_id가 없지만 book_review_info엔터티에는 book_id 컬럼이 있다.

이 경우 book_id 컬럼을 조인을 통해 양쪽에서 조회가 가능하다.

```
    select
        bookreview0_.id as id1_1_0_,
        bookreview0_.average_review_score as average_2_1_0_,
        bookreview0_.book_id as book_id4_1_0_,
        bookreview0_.review_count as review_c3_1_0_,
        book1_.id as id1_0_1_,
        book1_.author_id as author_i2_0_1_,
        book1_.category as category3_0_1_,
        book1_.created_at as created_4_0_1_,
        book1_.name as name5_0_1_,
        book1_.publisher_id as publishe6_0_1_,
        book1_.updated_at as updated_7_0_1_ 
    from
        book_review_info bookreview0_ 
    inner join
        book book1_ 
            on bookreview0_.book_id=book1_.id 
    where
        bookreview0_.id=?
```

=> join을 통해 book_id가 같은 데이터를 가져온다.

#### Book(OneToOne에 mappedBy)

```
package com.example.jpapractice.domain;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@NoArgsConstructor
@Data
@ToString(callSuper = true)
public class Book implements Common{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;

    private String category;

    private Long authorId;

    private Long publisherId;

    @OneToOne(mappedBy = "book")
    private BookReviewInfo bookReviewInfo;

    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;

}

```

 java.lang.StackOverflowError

![순환참조](C:\Users\multicampus\Downloads\순환참조.png)

=> Entity 리레이션을 사용하는 경우 toString Method가 순환 참조가 걸린다. 제외 처리가 필요 => @ToString.Exclude

```
    create table book (
       id bigint not null auto_increment,
        author_id bigint,
        category varchar(255),
        created_at datetime(6),
        name varchar(255),
        publisher_id bigint,
        updated_at datetime(6),
        primary key (id)
    ) 
```

=> DDL을 보면 book_review_info_id 컬럼이 없어진 것을 볼 수 있다.

#### BookReveiwInfo

```
package com.example.jpapractice.domain;

import lombok.Data;
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

```

```
    create table book_review_info (
       id bigint not null auto_increment,
        average_review_score float not null,
        review_count integer not null,
        book_id bigint not null,
        primary key (id)
    )
```

=> book_review_info테이블에는 book_id가 존재 하고


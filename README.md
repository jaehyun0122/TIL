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

@Column(updatable = false)

@Conlumn(insertable = false)

insert, update시 변경되지 않는다.



2. native qeury

@Query(vale="", native = true)

value의 쿼리문이 실행된다.
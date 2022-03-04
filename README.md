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

### jpa application.yml setting 

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




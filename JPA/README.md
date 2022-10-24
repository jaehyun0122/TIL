## 객체지향 언어
어플리케이션 ( 객체지향 언어 ) : 자바, 스칼라, ...
데이터베이스 ( 관계형 ) : MySql, Oracle, ...

**=> 객체를 관계형 DB에 저장**

### 1. SQL 중심의 개발
- 코드의 무한 반복
- CRUD : 
		필드가 추가 될 시 쿼리 수정이 이뤄진다.
        필드 수정을 까먹을 수도 있다.
        
        
        public class Member{
        	private Long id;
            private String name;
       		...
        }
        
        insert into Member values(...);
        
 => 만약 Member 필드가 변경되면 SQL까지 전부 변경해야한다.
 => SQL 중심적인 개발을 피하기 힘듬.
### 2. 객체 신뢰 문제
```
Member member = memberDao.find(id);
member.getTeam();
member.getOrder();
```
=> team, order에 null이 반환 
=> 조회에 따른 여러 메소드 생성

---
## JPA
- Java Persistence Api
- Java ORM 기술 표준
EJB(자바 표준, 성능 문제) => Hibernate(오픈소스) => JPA(자바 표준)
- JPA는 인터페이스 모음
- 자바 컬렉션에 저장하듯 DB에 저장할 수 있는 방법

## ORM
- Object Relational Mapping
- ORM 프레임워크가 애플리케이션과 JDBC사이에서 동작

## 객체와 관계형 데이터베이스의 차이
### 1. 상속
데이터베이스도 있지만 다른 개념이라 없다고 봄.
### 2. 연관관계
객체 : 참조 사용
테이블 : 외래키 ( 조인 )

### 3. 데이터 타입

### 4. 데이터 식별 방법

---

## Why JPA
- 생산성 : 쿼리 자동 생성 해준다.
- 유지보수 : 필드 변경시 모든 SQL을 수정할 필요 없다.
- 패러다임 불일치 해결
- 성능 최적화 기능
    - 1차 캐시와 동일성 보장 : 같은 트랜잭션 안에서는 같은 엔티티 반환 => 약간의 성능 향상
    - 쓰기 지연
    ```
    transaction.begin();
    em.persist(a);
    em.persist(b); 
    ...
    
    transaction.commit();
    ```
    - 지연로딩 즉시로딩 => 옵션을 선택 가능.
    지연로딩 : 객체가 실제 사용될 때 로딩
    ```
    Member member = memberDao.find(id); => select m from member m;
    Team team = member.getTeam();
    String teamName = team.getName(); => select t from team t;
    ```
    즉시로딩 : 조인으로 연관된 객체 미리 조회
    ```
    Member member = memberDao.find(id); => select m,t 
    										from member m join Team t;
    ```

### dialect
관계형 데이터베이스에서 MySql, Oracle을 대부분 사용중이다.
이 둘은 관계형 데이터베이스라는 공통점이 있지만 문법에서 차이를 보인다.

|| Mysql | Oracle |
|--|--|--|
|가변문자열|varchar|varchar2|
|페이지|limit|rownum|
|문자열자르기|substring|substr|

jpa에서는 이러한 sql 방언에 대해 자동으로 sql을 생성해준다.

```
@Entity
public class Member {

    @Id
    private Long id;

    private String name;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
```
#### INSERT
```
 // 1. Persistence를 이용해 EntityManagerFactory 생성
        EntityManagerFactory emf = Persistence.createEntityManagerFactory("hello");
        // 2. emf를 이용해 entitymanager 생성
        EntityManager em = emf.createEntityManager();

        EntityTransaction tx = em.getTransaction();
        tx.begin();

        // Code
        Member member = new Member();
        member.setId(1L);
        member.setName("member1");

        em.persist(member);
        tx.commit();

        em.clear();

        emf.close();
```
=> 결과
```
Hibernate: 
    /* insert jpabasic.jpa.helloJpa.Member
        */ insert 
        into
            Member
            (name, id) 
        values
            (?, ?)
```
#### SELECT
```
        try {
            Member findMember = em.find(Member.class, 1L);

            tx.commit();
        }catch (Exception e){
            tx.rollback();
        }finally {
            em.close();
        }
```
=> 결과
```
Hibernate: 
    select
        member0_.id as id1_0_0_,
        member0_.name as name2_0_0_ 
    from
        Member member0_ 
    where
        member0_.id=?
```
#### UPDATE
```
        try {
            Member findMember = em.find(Member.class, 1L);
          findMember.setName("updateName");

            tx.commit();
        }catch (Exception e){
            tx.rollback();
        }finally {
            em.close();
        }
```
=> Member 객체에 setName 후 em.persist를 하지 않는다.
=> 자동으로 update 쿼리를 생성.
```
    /* update
        jpabasic.jpa.helloJpa.Member */ update
            Member 
        set
            name=? 
        where
            id=?
```

---

## 영속성 컨텍스트
JPA에서 중요한 점
- ORM
- 영속성 컨텍스트

영속성 컨텐스트란 엔티티를 영구 저장하는 환경. 애플리케이션과 데이터베이스 사이에서 객체를 보관하는 가상의 데이터베이스 같은 역할. 엔티티 매니저를 통해 엔티티를 저장하거나 조회하면 엔티티 매니저는 영속성 컨텍스트에 엔티티를 보관하고 관리한다.
![](https://velog.velcdn.com/images/jjeom122/post/cd4efe26-5d92-46d1-9416-f656d9a49d49/image.png)

EntityManagerFactory => EntityManager => DB접근


- 비영속 
객체를 생성한 상태
Member m = new Member();
m.id = 1;
m.name = "asd";

- 영속
비영속 상태의 객체를 저장한 상태
EntityManager.persist(m);

- 준영속, 삭제

### 영속성 컨텍스트의 이점
- 1차 캐시
트랜잭션 commit 시점 전에 1차 캐시에 보관해 조회.
```
Member member = new Member();
member.setId(1L);
member.setName("name1");

em.persist(member);

Member findMember = em.find(1L); => 1차 캐시에서 조회 / select 쿼리 생성 없이 조회
=> 조회한 데이터가 영속성 컨텍스트에 없다면 영속 상태로 저장
```
=> 트랜잭션이 종료되면 1차 캐시도 없어지기 때문에 성능상 큰 이점은 없다.
- 동일성 보장
```
Member m1 = em.find(Member.class, "m1");
Member m2 = em.find(Member.class, "m1");

System.out.print(m1 == m2) => true
```
- 트랜잭션을 지원하는 쓰기 지연
commit 시점에 데이터베이스에 반영
```
em.persist(member); => 1차 캐시에 저장
tx.commit(); => commit 시점에 SQL 쿼리 생성
```
- 변경 감지
```
        try {
            Member findMember = em.find(Member.class, 1L);
          findMember.setName("updateName");

            tx.commit();
        }catch (Exception e){
            tx.rollback();
        }finally {
            em.close();
        }
```
=> Member 객체에 setName 후 em.persist를 하지 않는다.
=> 자동으로 update 쿼리를 생성.
```
    /* update
        jpabasic.jpa.helloJpa.Member */ update
            Member 
        set
            name=? 
        where
            id=?
```
flush => Entity와 snapshot(처음 읽어온 데이터) 비교 => 쓰기 지연 저장소에 저장 => commit => flush

##### FLUSH
   - 영속성 컨텍스트 변경내용을 DB에 반영
   - 영속성 컨텍스트 유지

---

## Entity Mapping
- 객체, 테이블
- 필드, 컬럼
- 기본키
- 연관관계

### Entity, Table
@Entity => JPA가 관리
- 기본생성자 필수

@Table 
- Entity와 매핑할 테이블 지정
- @Table(name = "name")

### DB Schema 자동생성
- 애플리케이션 실행 시점에 자동생성
- DB 방언에 맞는 DDL 생성
- 옵션
    - create : 기존 테이블 삭제 후 생성
    - create-drop : 종료시점에 테이블 drop
    - update : 변경사항만 반영 => alter table 
    - validate : Entity와 Table이 정상 매핑 여부 확인
    - none : 미사용
    - 운영서버는 validate or none
    => 데이터 변경 시 서버 오류 위험

### 컬럼 매핑
- Id : 기본 키
- Column(name = "") : 컬럼 명 지정
- Enumerated : enum type
- Temporal : 시간
- Lob : varchar를 넘어서는 문자 (blob, clob)
- Transient : 컬럼 반영 x

### 연관관계 매핑
연관관계 매핑 전
```
Team team = new Team();
team.setName("team1");
em.persist(team);

Member member = new Member();
member.setName("member1");
member.setTeam(team.getId());
em.persist(member);
```
매핑 후
```
Team team = new Team();
team.setName("team1");
em.persist(team);

Member member = new Member();
member.setName("member1");
member.setTeam(team);
em.persist(member);
```

### 양방향 매핑
|Member|Team|
|-|-|
|id|id|
|Team team|List members|
|name|name|
|N|1|
Member와 Team 양방향 연관관계 설정 시 탐색 가능.
테이블은 FK로 두 테이블 연관관계를 관리할 수 있다.
**객체의 경우 둘 중 하나로 외래 키를 관리해야 함**.
- 객체 하나를 주인으로 지정
- 주인만이 외래 키 관리 => 주인은 외래 키가 있는 곳으로 지정
- 주인이 아닌 경우 읽기만 가능 => mappedBy로 주인을 지정

#### mappedBy
데이터베이스 테이블에서는 양방향 연관관계( 외래키를 사용해 )를 설정할 수 있다.
하지만 객체의 양방향 관계는 단방향 관계 2개이다.

멤버와 팀 테이블이 연관관계가 있다면 하나의 데이터가 수정되었을 때 신뢰할 수 있는 데이터를 알 수 없다.

두 관계중 하나를 연관관계 주인으로 설정해야된다.
mappedBy를 사용한 쪽이 종이 된다. => 주인이 아닌 쪽은 읽기만 가능. set을 할 수 없다. DB에 반영이 안됨.

#### 양쪽에 값을 설정
```
Team team = new Team();
team.setName("team1");
em.persist(team);

Member member = new Member();
member.setName("member1");
member.setTeam(team); => **팀 지정 시 팀의 members에 member 세팅**
em.persist(member);
```
```
@Entity
public class Member {

    @Id
    @Column(name = "member_id")
    private Long id;
    private String name;
    @ManyToOne
    @JoinColumn(name = "team_id")
    private Team team;
    
    ...
    pulbic void setTeam(Team team){
    	this.team = team;
        team.getMembers().add(this);
    }

}
```


#### 양방향 매핑
단방향 매핑만으로도 연관관계 완료.
JPQL에서 역방향으로 탐색할 일이 많음.
단방향 매핑을 하고 필요 시 양방향으로.

--- 

### JPQL
SQL을 추상화한 객체 지향 쿼리언어.
엔티티 객체를 대상으로 검색.

### QueryDSL
SQL, JPQL 해당 로직 실행 전까지 작동여부 확인 불가( 오류 ).

**장점**
문자가 아닌 코드로 작성
컴파일 시점 문법 오류 발견
동적 쿼리


쿼리에 메소드를 사용할 수도 있다.
```
query.selectFrom(m)
	.where(
    	m.type.eq(typeParam),
        isValid()
    )
    .fetch();
    

private BooleanExpression isValid(){
	return m.status.eq("A");
}
```

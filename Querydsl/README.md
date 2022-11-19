출처 [실전! Querydsl](https://www.inflearn.com/course/querydsl-%EC%8B%A4%EC%A0%84/dashboard)

쿼리를 자바 코드로 작성
- 코드 오류를 컴파일 과정에서 확인
- 복잡한 쿼리 작성

SpringBoot 2.6 이상버전 오류
```
Unable to load class 'com.mysema.codegen.model.Type'.
```

dependency에 다음과 같이 추가
    implementation "com.querydsl:querydsl-jpa:${querydslVersion}"
    implementation "com.querydsl:querydsl-apt:${querydslVersion}"

```
buildscript {
	ext {
		queryDslVersion = "5.0.0"
	}
}

plugins {
	id 'org.springframework.boot' version '2.6.13'
	id 'io.spring.dependency-management' version '1.0.15.RELEASE'
	//querydsl 추가
	id "com.ewerk.gradle.plugins.querydsl" version "1.0.10"
	id 'java'
}

group = 'com.example'
version = '0.0.1-SNAPSHOT'
sourceCompatibility = '11'

configurations {
	compileOnly {
		extendsFrom annotationProcessor
	}
}

repositories {
	mavenCentral()
}

dependencies {
	implementation 'org.springframework.boot:spring-boot-starter-data-jpa'
	implementation 'org.springframework.boot:spring-boot-starter-web'
	compileOnly 'org.projectlombok:lombok'
	runtimeOnly 'com.h2database:h2'
	//querydsl 추가
	implementation "com.querydsl:querydsl-jpa:${queryDslVersion}"
	annotationProcessor "com.querydsl:querydsl-apt:${queryDslVersion}"
	// query ? 확인 편리
	implementation 'com.github.gavlyukovskiy:p6spy-spring-boot-starter:1.5.8'
	annotationProcessor 'org.projectlombok:lombok'
	testImplementation 'org.springframework.boot:spring-boot-starter-test'
}

tasks.named('test') {
	useJUnitPlatform()
}

//querydsl build 관련 추가 시작
def querydslDir = "$buildDir/generated/querydsl"
querydsl {
	jpa = true
	querydslSourcesDir = querydslDir
}
sourceSets {
	main.java.srcDir querydslDir
}
configurations {
	querydsl.extendsFrom compileClasspath
}
compileQuerydsl {
	options.annotationProcessorPath = configurations.querydsl
}
//querydsl 추가 끝

```




Qfile 생성

=> gradle => Tasks => other => compileQuerydsl

![image-20221116220912968](https://github.com/jaehyun0122/TIL/blob/master/Querydsl/asset/compileQuerydsl.png)

**build -> generated에 Qfile 생성**

# Querydsl vs JPQL

```
    @BeforeEach
    public void before(){
        queryFactory = new JPAQueryFactory(em);
        Team teamA = new Team("teamA");
        Team teamB = new Team("teamB");
        em.persist(teamA);
        em.persist(teamB);

        Member member1 = new Member("member1", 20, teamA);
        Member member2 = new Member("member2", 20, teamA);
        Member member3 = new Member("member3", 100, teamB);
        Member member4 = new Member("member4", 100, teamB);

        em.persist(member1);
        em.persist(member2);
        em.persist(member3);
        em.persist(member4);
    }
```



```
    @Test
    public void startJPQL(){
        //member1 찾기
        Member findMember = em.createQuery("select m from Member m where m.username= :username", Member.class)
                .setParameter("username", "member1")
                .getSingleResult();

        Assertions.assertThat(findMember.getUsername()).isEqualTo("member1");

    }

    @Test
    public void startQuerydsl(){
        JPAQueryFactory queryFactory = new JPAQueryFactory(em);
        QMember m = new QMember("m");

        Member findMember = queryFactory
                .select(m)
                .from(m)
                .where(m.username.eq("member1"))
                .fetchOne();

        Assertions.assertThat(findMember.getUsername()).isEqualTo("member1");
    }
```


- querydsl 사용 시 컴파일 시점에서 오류를 확인할 수 있다.
- JPQL은 문자로 쿼리를 생성하지만 Querydsl은 preparedStatement를 사용해 SQL Insection을 예방할 수 있다.

1. Q-Type 활용
- QMember m = new QMember("m");
- QMember m = QMember.member;
- Alias가 중복되면 안되는 경우 Qtype 생성

=> static import 활용 권장
```
import static com.example.querydsl.entity.QMember.member;
...

    @Test
    public void startQuerydsl(){
        Member findMember = queryFactory
                .select(member)
                .from(member)
                .where(member.username.eq("member1"))
                .fetchOne();

        Assertions.assertThat(findMember.getUsername()).isEqualTo("member1");
    }
```

### 검색 쿼리
- eq : ==
- ne : !=
- isNotNull : notnull
- in(10, 20) : in (10, 20)
- notIn(10, 20) : not 10, 20
- between(10, 30) : 10~30
- goe(30) : grater of equal  30<=
- gt(30) : grater 30<
- loe(30) : lower or equal >=30
- lt(30) : >30
- like("member%")
- contains("member") : %member%
- startWith("member") : member%


#### and 조건 
1. 
```
Member findMember = queryFactory
                .selectFrom(member)
                .where(member.username.eq("member1").and(member.age.eq(20)))
                .fetchOne();
```

2.
```
        Member findMember = queryFactory
                .selectFrom(member)
                .where(
                        member.username.eq("member1"),
                        member.age.eq(20)
                )
                .fetchOne();
```

### 결과 조회
- fetch : 리스트 조회, 데이터 없으면 빈 리스트 반환
- fetchOne : 하나의 데이터 조회
  - 결과 없으면 : null
  - 둘 이상이면 : nonuniqueuresultException
- fetchFirst() : limit(1).fetchOne()
- ~~fetchResults() : 페이징 정보 포함~~ => desprecated
- ~~fetchCount() : count 쿼리로 변경해 count 수 조회~~ => desprecated

### 정렬

```
List<Member> result = queryFactory
    .selectFrom(member)
    .where(member.age.eq(100))              	                        
    .orderBy(member.username.asc().nullsLast(), member.age.desc()) // 이름 오름차순, null 마지막 / 나이 내림차순
     .fetch();
```



### 페이징

```
    @Test
    public void paging(){
        List<Member> result = queryFactory
                .selectFrom(member)
                .orderBy(member.username.desc())
                .offset(1)
                .limit(2)
                .fetch();

        Assertions.assertThat(result.size()).isEqualTo(2);
```



### 집합

```
    @Test
    public void aggregation(){
        List<Tuple> result = queryFactory
                .select(
                        member.count(),
                        member.age.sum(),
                        member.age.avg(),
                        member.age.max(),
                        member.age.min()
                )
                .from(member)
                .fetch();

        Tuple tuple = result.get(0);
//        System.out.println("tuple : "+tuple);
//        tuple : [4, 240, 60.0, 100, 20]

        Assertions.assertThat(tuple.get(member.count())).isEqualTo(4);
        Assertions.assertThat(tuple.get(member.age.sum())).isEqualTo(240);
        Assertions.assertThat(tuple.get(member.age.avg())).isEqualTo(60);
        Assertions.assertThat(tuple.get(member.age.max())).isEqualTo(100);
        Assertions.assertThat(tuple.get(member.age.min())).isEqualTo(20);

    }
```

```
    @Test
    public void group() throws Exception{
        // 팀 이름과 각 팀의 평균 연령
        List<Tuple> result = queryFactory
                .select(team.name, member.age.avg())
                .from(team)
                .join(member).on(team.eq(member.team))
                .groupBy(team.name)
                .fetch();

        Tuple teamA = result.get(0);
        Tuple teamB = result.get(1);

        Assertions.assertThat(teamA.get(team.name)).isEqualTo("teamA");
        Assertions.assertThat(teamA.get(member.age.avg())).isEqualTo(20);

        Assertions.assertThat(teamB.get(team.name)).isEqualTo("teamB");
        Assertions.assertThat(teamB.get(member.age.avg())).isEqualTo(100);
        
    }
```



### Join

```
    @Test
    public void join() throws Exception{
        List<Tuple> teamA = queryFactory
                .select(member, team)
                .from(member)
                .leftJoin(member.team, team).on(team.name.eq("teamA")) // 연관관계가 있기 때문에 member.team을 별칭 : team으로 해서 조인
                .fetch();

        for (Tuple tuple : teamA) {
            System.out.println("tuple : "+tuple);
        }
    }
```

```
tuple : [Member(id=3, username=member1, age=20), Team(name=teamA)]
tuple : [Member(id=4, username=member2, age=20), Team(name=teamA)]
tuple : [Member(id=5, username=member3, age=100), null]
tuple : [Member(id=6, username=member4, age=100), null]
```

#### 페치 조인

- SQL에서 제공하는 기능은 아님
- 조인을 활용해 연관된 엔터티를 가져옴
- 성능 최적화에 사용

```
    @PersistenceUnit
    EntityManagerFactory emf;

    @Test
    public void fetchJoinNo(){
        em.flush();
        em.clear();

        Member findMember = queryFactory
                .selectFrom(member)
                .where(member.username.eq("member1"))
                .fetchOne();

        boolean loaded = emf.getPersistenceUnitUtil().isLoaded(findMember.getTeam()); // findMember.getTeam()이 이미 로딩된 엔터티인지
        assertThat(loaded).isFalse();

    }
```

```
    select
        member0_.member_id as member_i1_1_,
        member0_.age as age2_1_,
        member0_.team_id as team_id4_1_,
        member0_.username as username3_1_ 
    from
        member member0_ 
    where
        member0_.username=?
```



fetchJoin 사용

```
    @Test
    public void fetchJoinYes(){
        em.flush();
        em.clear();

        Member findMember = queryFactory
                .selectFrom(member)
                .join(member.team, team).fetchJoin()
                .where(member.username.eq("member1"))
                .fetchOne();

        boolean loaded = emf.getPersistenceUnitUtil().isLoaded(findMember.getTeam()); // findMember.getTeam()이 이미 로딩된 엔터티인지
        assertThat(loaded).isTrue();

    }
```

```
    select
        member0_.member_id as member_i1_1_0_,
        team1_.id as id1_2_1_,
        member0_.age as age2_1_0_,
        member0_.team_id as team_id4_1_0_,
        member0_.username as username3_1_0_,
        team1_.name as name2_2_1_ 
    from
        member member0_ 
    inner join
        team team1_ 
            on member0_.team_id=team1_.id 
    where
        member0_.username=?
```



### 서브 쿼리

**JPAExpressions 사용**

```
import com.querydsl.jpa.JPAExpressions;
```

```
    @Test
    public void subQuery(){
        // 나이 가장 많은 회원 조회
        QMember memberSub = new QMember("memberSub"); // subQuery의 alias와 겹치면 않되므로 생성

        List<Member> result = queryFactory
                .selectFrom(member)
                .where(member.age.eq(
                        JPAExpressions
                                .select(memberSub.age.max())
                                .from(memberSub)
                ))
                .fetch();

        assertThat(result).extracting("age")
                .containsExactly(100, 100);
    }
```

```
    select
        member0_.member_id as member_i1_1_,
        member0_.age as age2_1_,
        member0_.team_id as team_id4_1_,
        member0_.username as username3_1_ 
    from
        member member0_ 
    where
        member0_.age=(
            select
                max(member1_.age) 
            from
                member member1_
        )
```



**JPA JPQL(Querydsl) 서브쿼리의 한계로 from절에서 서브쿼리 지원하지 않는다. 하이버네이트 구현체 사용하면 select절의 서**

- 서브쿼리를 join으로
- 쿼리를 2번 분리 실행
- nativeSQL 사용



### case

- when
- new CaseBuilder()



**when**

```
    @Test
    public void basicCase(){
        List<String> result = queryFactory
                .select(member.age
                        .when(20).then("20살")
                        .when(100).then("100살")
                        .otherwise("기타")
                )
                .from(member)
                .fetch();

        for (String s : result) {
            System.out.println(s);
        }
    }
```

```
20살
20살
100살
100살
```

**CaseBuilder()**

```
    @Test
    public void caseBuilder(){
        List<String> result = queryFactory
                .select(new CaseBuilder()
                        .when(member.age.between(10, 20)).then("10~20살")
                        .when(member.age.gt(20)).then("21살 이상")
                        .otherwise("기타")
                )
                .from(member)
                .fetch();

        for (String s : result) {
            System.out.println(s);
        }
    }
```

```
10~20살
10~20살
21살 이상
21살 이상
```



### 문자열

**concat**

```
    @Test
    public void concat(){

        List<String> result = queryFactory
                .select(member.username.concat("_").concat(member.age.stringValue()))
                .from(member)
                .fetch();

        for (String s : result) {
            System.out.println(s);
        }
    }
```

```
member1_2
member2_2
member3_1
member4_1
```



## 중급 문법

### 프로젝션

select구문에 작성

- 프로젝션 하나

  - 타입을 명확히 지정 가능

    ```
     @Test
        public void simpleProjection(){
            List<String> result = queryFactory
                    .select(member.username)
                    .from(member)
                    .fetch();
    
            for (String s : result) {
                System.out.println(s);
            }
    ```

    ```
        select
            member0_.username as col_0_0_ 
        from
            member member0_
    ```

    

- 둘 이상

  - 튜플이나 DTO로 조회
  - 튜플은 querydsl에 종속적 타입이므로 repository안에서만 사용하고 DTO 사용 권장

  ```
      @Test
      public void tupleProjection(){
          List<Tuple> result = queryFactory
                  .select(member.username, member.age)
                  .from(member)
                  .fetch();
  
          for (Tuple tuple : result) {
              String username = tuple.get(member.username);
              Integer age = tuple.get(member.age);
              System.out.println("usernmae : "+username+" age : "+age);
          }
      }
  ```

  ```
      select
          member0_.username as col_0_0_,
          member0_.age as col_1_0_ 
      from
          member member0_
  ```



### DTO 조회

- JPQL

  - new 명령 사용
  - DTO package이름 다 적어야됨

  ```
  List<MemberDTO> result = em.createQuery("select new com.example.querydsl.dto.MemberDTO(m.username, m.age) from Member m", MemberDTO.class)
                  .getResultList();
  ```

  

- Querydsl 빈

  - 프로퍼티

    ```
        @Test
        public void findDtoBySetter(){
            List<MemberDTO> result = queryFactory
                    .select(Projections.bean(MemberDTO.class,
                            member.username, member.age))
                    .from(member)
                    .fetch();
        }
    ```

    

  - 필드

  ```
      @Test
      public void findDtoByField(){
          List<MemberDTO> result = queryFactory
                  .select(Projections.fields(MemberDTO.class, // getter setter 없어도 field에 데이터 입력
                          member.username, member.age))
                  .from(member)
                  .fetch();
      }
  ```

  

  - 생성자

```
    @Test
    public void findDtoByConstructor(){
        List<MemberDTO> result = queryFactory
                .select(Projections.constructor(MemberDTO.class,
                        member.username, member.age))
                .from(member)
                .fetch();
    }
```



### @QueryProjection

DTO에 @QueryProjection

```
@Data
public class MemberDTO {

    private String username;
    private int age;

    public MemberDTO(){

    }

    @QueryProjection
    public MemberDTO(String username, int age) {
        this.username = username;
        this.age = age;
    }
}
```

-> **compileQuerydsl - Qtype생성**

```
    @Test
    public void findDtoQueryProjection(){
        List<MemberDTO> result = queryFactory
                .select(new QMemberDTO(member.username, member.age))
                .from(member)
                .fetch();
```

=> 생성자 방식과 비슷하지만 컴파일 과정에서 오류 발견 가능

=> 생성자 방식은 런타임 오류

### 동적 쿼리

- BooleanBuilder
- Where 다중 파라미터

#### BooleanBuilder

```
    @Test
    public void dynamicQuery_BooleanBuilder(){
        String usernameParam = "member1";
        Integer ageParam = 20;

        List<Member> result = searchMember1(usernameParam, ageParam);
        assertThat(result.size()).isEqualTo(1);
    }

    private List<Member> searchMember1(String usernameParam, Integer ageParam) {
        BooleanBuilder builder = new BooleanBuilder();
        if(usernameParam != null){
            builder.and(member.username.eq(usernameParam));
        }
        if(ageParam != null){
            builder.and(member.age.eq(ageParam));
        }

        List<Member> result = queryFactory
                .selectFrom(member)
                .where(builder)
                .fetch();
        return result;
    }
```

```
    select
        member0_.member_id as member_i1_1_,
        member0_.age as age2_1_,
        member0_.team_id as team_id4_1_,
        member0_.username as username3_1_ 
    from
        member member0_ 
    where
        member0_.username=? 
        and member0_.age=?
```

=> 파라미터에 null이 들어오면 조건에서 제외

=> age가 null인 경우

```
    select
        member0_.member_id as member_i1_1_,
        member0_.age as age2_1_,
        member0_.team_id as team_id4_1_,
        member0_.username as username3_1_ 
    from
        member member0_ 
    where
        member0_.username=?
```



#### Where 다중 파라미터

**코드 깔끔해진다**

=> where 조건에 null이 들어오면 무시된다.

=> 동적 쿼리

```
    @Test
    public void dynamicQuery_Where(){
        String usernameParam = "member1";
        Integer ageParam = 20;

        List<Member> result = searchMember2(usernameParam, ageParam);
        assertThat(result.size()).isEqualTo(1);
    }

    private List<Member> searchMember2(String usernameParam, Integer ageParam) {
        return queryFactory
                .selectFrom(member)
                .where(usernameEq(usernameParam), ageEq(ageParam)) // null param이 들어오면 무시
                .fetch();
    }

    private Predicate ageEq(Integer ageParam) {
        if(ageParam != null){
            return member.age.eq(ageParam);
        }
        return null;
    }

    private Predicate usernameEq(String usernameParam) {
        if(usernameParam != null){
            return member.username.eq(usernameParam);
        }
        return null;
    }
```

=> 메소드로 작성되기 때문에 조립 가능

```
private BooleanExpression allEq(String usernameParam, Integer ageParam){
        return usernameEq(usernameParam).and(ageEq(ageParam));
    }
```



## Spring Data Jpa & Querydsl

![image-20221116224607556](https://github.com/jaehyun0122/TIL/blob/master/Querydsl/asset/extends.png)

1. MemberRepo interface에 JpaRepository & MemberRepoCustom 상속

MemberRepo

```
public interface MemberRepo extends JpaRepository<Member, Long>, MemberRepoCustom {
    List<Member> findByUsername(String username);
}
```

MemberRepoCustom

```
public interface MemberRepoCustom {
    List<MemberTeamDto> search(MemberSearchCondition condition);
}MemberRepo 구현체를 만들어 코드 작성
```

2. MemberRepoCustom 구현체를 만들어 코드 작성

```
public class MemberRepoImpl implements MemberRepoCustom{
    
    private final JPQLQueryFactory queryFactory;

    public MemberRepoImpl(EntityManager em) {
        this.queryFactory = new JPAQueryFactory(em);
    }

    @Override
    public List<MemberTeamDto> search(MemberSearchCondition condition){
        return queryFactory
                .select(new QMemberTeamDto(
                        member.id.as("memberId"),
                        member.username,
                        member.age,
                        team.id.as("teamId"),
                        team.name.as("teamName")
                ))
                .from(member)
                .leftJoin(member.team, team)
                .where(usernameEq(condition.getUsername()),
                        teamNameEq(condition.getTeamName()),
                        ageGoe(condition.getAgeGoe()),
                        ageLoe(condition.getAgeLoe()))
                .fetch();
    }

    private Predicate ageLoe(Integer ageLoe) {
        return ageLoe != null ? member.age.loe(ageLoe) : null;
    }

    private Predicate ageGoe(Integer ageGoe) {
        return ageGoe != null ? member.age.goe(ageGoe) : null;
    }

    private Predicate teamNameEq(String teamName) {
        return hasText(teamName) ? team.name.eq(teamName) : null;
    }

    private Predicate usernameEq(String username) {
        return hasText(username) ?  member.username.eq(username) : null;
    }
    
}
```

### Spring Data Paging

- Page, Pageable 활용

```
public interface MemberRepoCustom {
    List<MemberTeamDto> search(MemberSearchCondition condition);
    Page<MemberTeamDto> searchPageSimple(MemberSearchCondition condition, Pageable pageable);
    Page<MemberTeamDto> searchPageComplex(MemberSearchCondition condition, Pageable pageable);
}
```

구현체

```
    @Override
    public Page<MemberTeamDto> searchPageSimple(MemberSearchCondition condition, Pageable pageable) {
        QueryResults<MemberTeamDto> results = queryFactory
                .select(new QMemberTeamDto(
                        member.id.as("memberId"),
                        member.username,
                        member.age,
                        team.id.as("teamId"),
                        team.name.as("teamName")
                ))
                .from(member)
                .leftJoin(member.team, team)
                .where(usernameEq(condition.getUsername()),
                        teamNameEq(condition.getTeamName()),
                        ageGoe(condition.getAgeGoe()),
                        ageLoe(condition.getAgeLoe()))
                .offset(pageable.getOffset())
                .limit(pageable.getPageSize())
                .fetchResults();

        List<MemberTeamDto> content = results.getResults();
        long total = results.getTotal();

        return new PageImpl<>(content, pageable, total);
    }
```

**Test**

PageRequest를 파라미터로 넘겨주면 된다.

```
    @Test
    public void searchSimpleTest(){
        Team teamA = new Team("teamA");
        Team teamB = new Team("teamB");
        em.persist(teamA);
        em.persist(teamB);

        Member member1 = new Member("member1", 20, teamA);
        Member member2 = new Member("member2", 20, teamA);
        Member member3 = new Member("member3", 50, teamB);
        Member member4 = new Member("member4", 100, teamB);

        em.persist(member1);
        em.persist(member2);
        em.persist(member3);
        em.persist(member4);

        MemberSearchCondition condition = new MemberSearchCondition();
        PageRequest pageRequest = PageRequest.of(0, 3);

        Page<MemberTeamDto> result1 = memberRepo.searchPageSimple(condition, pageRequest);
        assertThat(result1.getSize()).isEqualTo(3);
        assertThat(result1.getContent()).extracting("username").containsExactly("member1", "member2", "member3");
    }
```

### Count 쿼리

count 쿼리 생각 가능한 경우 -> count 쿼리를 생략해 성능 향상

- 페이지 시작이면서 컨텐츠 사이즈가 페이지 사이즈 보다 작을 때 (한 페이지에 50개 보여주는데 데이터가 50개 미만인 경우)
- 마지막 페이지 (offet+컨텐츠 사이즈를 더해 구함)

기존 코드

```
long total = queryFactory
                .select(member)
                .from(member)
                .leftJoin(member.team, team)
                .where(usernameEq(condition.getUsername()),
                        teamNameEq(condition.getTeamName()),
                        ageGoe(condition.getAgeGoe()),
                        ageLoe(condition.getAgeLoe()))
                        .fetchCount();
                        
return new PageImpl<>(content, pageable, total);                        
```

변경

```
JPQLQuery<Member> countQuery = queryFactory
                .select(member)
                .from(member)
                .leftJoin(member.team, team)
                .where(usernameEq(condition.getUsername()),
                        teamNameEq(condition.getTeamName()),
                        ageGoe(condition.getAgeGoe()),
                        ageLoe(condition.getAgeLoe()));
                        
return PageableExecutionUtils.getPage(content, pageable, countQuery::fetchCount);
```


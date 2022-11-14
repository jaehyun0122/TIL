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


Qfile 생성
```
package com.example.querydsl.entity;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;


/**
 * QHello is a Querydsl query type for Hello
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QHello extends EntityPathBase<Hello> {

    private static final long serialVersionUID = -932168945L;

    public static final QHello hello = new QHello("hello");

    public final NumberPath<Long> Id = createNumber("Id", Long.class);

    public QHello(String variable) {
        super(Hello.class, forVariable(variable));
    }

    public QHello(Path<? extends Hello> path) {
        super(path.getType(), path.getMetadata());
    }

    public QHello(PathMetadata metadata) {
        super(Hello.class, metadata);
    }

}
```

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
- ~~fetchResults() : 페이징 정보 포함~~ => deprecated
- ~~fetchCount() : count 쿼리로 변경해 count 수 조회~~ => desprecated

### 정렬

```
List<Member> result = queryFactory
    .selectFrom(member)
    .where(member.age.eq(100))              	                        .orderBy(member.username.asc().nullsLast(), member.age.desc()) // 이름 오름차순, null 마지막 / 나이 내림차순
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


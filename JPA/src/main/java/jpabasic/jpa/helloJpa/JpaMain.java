package jpabasic.jpa.helloJpa;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;
import javax.persistence.Persistence;

public class JpaMain {
    public static void main(String[] args) {
        // 1. Persistence를 이용해 EntityManagerFactory 생성
        EntityManagerFactory emf = Persistence.createEntityManagerFactory("hello");
        // 2. emf를 이용해 entitymanager 생성
        EntityManager em = emf.createEntityManager();

        EntityTransaction tx = em.getTransaction();
        tx.begin();

        // Code
//        Member member = new Member();
//        member.setId(1L);
//        member.setName("member1");

        try {
            Member findMember = em.find(Member.class, 1L);
          findMember.setName("updateName");

            tx.commit();
        }catch (Exception e){
            tx.rollback();
        }finally {
            em.close();
        }

//        em.persist(member);

        emf.close();
    }
}

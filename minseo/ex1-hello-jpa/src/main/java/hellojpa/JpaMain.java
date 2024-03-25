package hellojpa;

import jakarta.persistence.*;

import java.time.LocalDateTime;
import java.util.List;

public class JpaMain {

    public static void main(String[] args) {

        EntityManagerFactory emf = Persistence.createEntityManagerFactory("hello");
        EntityManager em = emf.createEntityManager();

        EntityTransaction tx = em.getTransaction();
        tx.begin();

        try {

            Member member = new Member();
            member.setUsername("user1");

            em.persist(member);

            em.flush();
            em.clear();

//            Member findMember = em.find(Member.class, member.getId());
            Member findMember = em.getReference(Member.class, member.getId()); // 이 시점에는 쿼리가 나가지 않는다.
            System.out.println("findMember = " + findMember.getClass()); // 가짜클래스 -> 데이터베이스 조회를 미루는 프록시 엔티티 객체를 조회한다.

            System.out.println("findMember.id = " + findMember.getId()); // id는 위에서 사용한 값을 그대로 가져온다.
            System.out.println("findMember.username = " + findMember.getUsername()); // username을 호출하는 시점에 쿼리를 날린다.

            tx.commit();
        } catch (Exception e) {
            tx.rollback();
        } finally {
            em.close();
        }

        emf.close();
    }
}

package hellojpa;

import jakarta.persistence.*;

import java.util.List;

public class JpaMain {

    public static void main(String[] args) {

        EntityManagerFactory emf = Persistence.createEntityManagerFactory("hello");
        EntityManager em = emf.createEntityManager();

        EntityTransaction tx = em.getTransaction();
        tx.begin();

        try {
            // 회원 등록
            Member member = new Member();
            member.setId(2L);
            member.setName("helloB");

            em.persist(member);

            // 회원 조회 (JPQL)
            List<Member> result = em.createQuery("select m from Member as m", Member.class)
                            .getResultList();

            for (Member m : result ) {
                System.out.println("member.name = " + m.getName());
            }

            tx.commit();
        } catch (Exception e) {
            tx.rollback();
        } finally {
            em.close();
        }

        emf.close();
    }
}

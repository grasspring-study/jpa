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

            Member member = em.find(Member.class, 150L);
            member.setName("AAAAA"); // persist 하지 않아도 JPA에 의해서 변경사항이 알아서 update된다.

            em.clear(); // 엔티티 매니저 안에 있는 영속성 컨텍스트를 모두 지운다. 앞선 setName은 반영되지 않는다.

            Member member2 = em.find(Member.class, 150L); // 영속성 컨텍스트가 초기화 되어 있으므로 영속성 컨텍스트에 올리기 위해 쿼리가 또 나간다.

            System.out.println("====================");
            tx.commit();
        } catch (Exception e) {
            tx.rollback();
        } finally {
            em.close();
        }

        emf.close();
    }
}

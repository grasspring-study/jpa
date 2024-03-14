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

            // 저장
            Team team = new Team();
            team.setName("TeamA");
            em.persist(team); // 영속상태가 되면서 Pk값이 설정됨

            Member member = new Member();
            member.setUsername("member1");
            member.setTeam(team);
            em.persist(member);

//            em.flush();
//            em.clear();

            Member findMember = em.find(Member.class, member.getId()); // 영속성 컨텍스트로 인해 1차 캐시에서 가져오므로 select쿼리가 나가지 않는다.

            Team findTeam = findMember.getTeam();
            System.out.println("findTeam = " + findTeam.getName());

            tx.commit();
        } catch (Exception e) {
            tx.rollback();
        } finally {
            em.close();
        }

        emf.close();
    }
}

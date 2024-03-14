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
            em.persist(team);

            Member member = new Member();
            member.setUsername("member1");
            member.changeTeam(team); // 연관관계의 주인에게 값을 넣어 주어야 한다.
            em.persist(member);

//            em.flush();
//            em.clear();
            // 이 부분을 주석처리하면 영속성 컨텍스트에 의해 1차 캐시에서 find가 실행된다.
            // 이때 team.getMembers().add(member);를 넣지 않으면 team이 Member List를 가지고 있지 않기 때문에 아래 for문에서 아무것도 출력되지 않는다.
            // 따라서 양쪽에 값을 다 넣어주는 것이 좋다.

            Team findTeam = em.find(Team.class, team.getId());
            List<Member> members = findTeam.getMembers();

            System.out.println("==============");
            for (Member m : members) {
                System.out.println("m = " + m.getUsername());
            }
            System.out.println("==============");

            tx.commit();
        } catch (Exception e) {
            tx.rollback();
        } finally {
            em.close();
        }

        emf.close();
    }
}

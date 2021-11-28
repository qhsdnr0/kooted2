package kooted.kooted.repository;

import kooted.kooted.model.User;
import kooted.kooted.model.UserWorkingYear;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.List;

@Repository
@RequiredArgsConstructor
public class UserRepository {

    @PersistenceContext
    private final EntityManager em;

    public void save(User user) { em.persist(user); }

    public User findOne(Long id) {
        try {
            User user = em.find(User.class, id);
            if (user == null) {
                throw new IllegalStateException();
            }
            return user;
        } catch (IllegalStateException ex) {
            ex.printStackTrace();
            throw new IllegalStateException("USER_DOES_NOT_EXIST");
        }
    }

    public List<User> findByKakaoId(Long kakaoId) {
        return em.createQuery("select u from User u where kakaoId= :kakaoId", User.class)
                .setParameter("kakaoId", kakaoId)
                .getResultList();
    }

    public void saveUserWorkingYear(UserWorkingYear userWorkingYear) {
        em.persist(userWorkingYear);
    }
}
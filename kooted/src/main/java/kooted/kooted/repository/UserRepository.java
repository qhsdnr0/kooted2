package kooted.kooted.repository;

import kooted.kooted.model.*;
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

    public void saveBookMark(BookMark bookMark) {
        em.persist(bookMark);
    }

    public void deleteBookMark(BookMark bookMark) { em.remove(bookMark);}

    public List<BookMark> getBookMark(User user) {
        return em.createQuery("select p from User u join BookMark b on u= : user", BookMark.class)
                .setParameter("user", user)
                .getResultList();
    }

    public List<Object> getAverageSalaryByJob(Job job) {
        return em.createQuery("select coalesce((select avg(u.addInfo.salary) from User u join UserWorkingYear w on w.user=u " +
                        "group by w.job,w.workingYear having w.job=j and w.workingYear=y),0) as avg, j.name, y.years from WorkingYear y join Job j on j= :job order by y.years")
                .setParameter("job", job)
                .getResultList();

    }

    public List<Object> getAverageSalaryByJobGroup(JobGroup jobGroup) {
        return em.createQuery("select coalesce((select avg(u.addInfo.salary) from User u join UserWorkingYear w on w.user=u group by w.job.jobGroup,w.workingYear having w.job.jobGroup=j " +
                        "and w.workingYear=y),0) as avg, j.name, y.years from WorkingYear y join JobGroup j on j= :jobGroup order by y.years")
                .setParameter("jobGroup", jobGroup)
                .getResultList();
    }
}

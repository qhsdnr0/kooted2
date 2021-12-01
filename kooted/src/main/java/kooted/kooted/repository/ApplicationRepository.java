package kooted.kooted.repository;

import kooted.kooted.model.Application;
import kooted.kooted.model.User;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import java.util.HashMap;
import java.util.List;

@Repository
@RequiredArgsConstructor
public class ApplicationRepository {

    @PersistenceContext
    private final EntityManager em;

    public void save(Application application) { em.persist(application); }

    public Application findOne(Long id) { return em.find(Application.class, id); }

    public List<Application> findUserInfo(User user) {
        return em.createQuery("select a from Application a join User u on u= :user", Application.class)
                .setParameter("user", user)
                .getResultList();

    }
    
}

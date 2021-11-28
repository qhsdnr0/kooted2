package kooted.kooted.repository;

import kooted.kooted.model.Application;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

@Repository
@RequiredArgsConstructor
public class ApplicationRepository {

    @PersistenceContext
    private final EntityManager em;

    public void save(Application application) { em.persist(application); }

    public Application findOne(Long id) { return em.find(Application.class, id); }
    
}

package kooted.kooted.repository;

import kooted.kooted.model.Resume;
import kooted.kooted.model.User;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.List;

@Repository
@RequiredArgsConstructor
public class ResumeRepository {

    @PersistenceContext
    private final EntityManager em;

    public void save(Resume resume) { em.persist(resume); }

    public Resume findOne(Long id) { return em.find(Resume.class, id); }

    public List<Resume> findByUser(User user) {
        return em.createQuery("select r from Resume r where r.user= :user", Resume.class)
                .setParameter("user", user)
                .getResultList();
    }
}

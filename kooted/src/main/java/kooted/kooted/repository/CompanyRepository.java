package kooted.kooted.repository;

import kooted.kooted.model.Company;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

@Repository
@RequiredArgsConstructor
public class CompanyRepository {

    @PersistenceContext
    private final EntityManager em;

    public void save(Company company) {
        em.persist(company);
    }

    public Company findOne(Long id) {
        return em.find(Company.class, id);
    }
}

package kooted.kooted.repository;


import kooted.kooted.model.*;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;
import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;
import java.util.List;



@Repository
@RequiredArgsConstructor
public class PostRepository {

    private final EntityManager em;

    public void save(Post post) { em.persist(post);}

    public Post findOne(Long id) {
        return em.createQuery("select p from Post p join Company c on p.company=c where p.id= :id", Post.class)
                .setParameter("id", id)
                .getSingleResult();
    }

    public List<Post> findByJob(Job job, String sort, int limit, int offset) {
        if (sort.equals("count")) {
            TypedQuery<Post> query = em.createQuery("select p, count(b.id) from Post p join (select count(id) as cnt from BookMark group by id) b on b.post=p)", Post.class);
            return query.setFirstResult(offset)
                    .setMaxResults(limit)
                    .setParameter("job", job)
                    .setParameter("sort", sort)
                    .getResultList();
        } else {
            TypedQuery<Post> query = em.createQuery("select p from Post p where p.job=:job order by :sort", Post.class);
            return query.setFirstResult(offset)
                    .setMaxResults(limit)
                    .setParameter("job", job)
                    .setParameter("sort", sort)
                    .getResultList();
        }
    }

    public List<Post> findByJobGroup(JobGroup jobGroup, String sort, long limit, long offset) {
        if (sort.equals("count")) {
            return em.createQuery("select p from Post p join BookMark b on b.post= p where p.job.jobGroup= :jobGroup order by (select count(p) from b) offset= :offset, limit= :limit", Post.class)
                    .setParameter("jobGroup", jobGroup)
                    .setParameter("sort", sort)
                    .setParameter("limit", limit)
                    .setParameter("offset", offset)
                    .getResultList();
        } else {
            return em.createQuery("select p from Post p where p.job.jobGroup= :jobGroup order by :sort offset= :offset, limit= :limit", Post.class)
                    .setParameter("jobGroup", jobGroup)
                    .setParameter("sort", sort)
                    .setParameter("limit", limit)
                    .setParameter("offset", offset)
                    .getResultList();
        }
    }


    public List<Post> findByCompany(Company company) {
        return em.createQuery("select p from Post p where p.company = :company", Post.class)
                .setParameter("company", company)
                .getResultList();
    }

    public List<Post> findAll() {
        return em.createQuery("select p from Post p", Post.class)
                .getResultList();
    }
}

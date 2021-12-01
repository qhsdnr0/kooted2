package kooted.kooted.repository;


import kooted.kooted.model.*;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;
import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;
import java.util.ArrayList;
import java.util.Collection;
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

    public List<?> findByJob(Job job, String sort, int limit, int offset) {
        String sortValue = sort.equals("-cnt") ? "order by cnt desc" : String.format("order by %s", sort);


        return (em.createQuery("select p, coalesce((select count(b) from BookMark b where b.post=p),0) as cnt from Post p where job= :job " + sortValue)
                .setFirstResult(offset)
                .setMaxResults(limit)
                .setParameter("job", job)
//                .setParameter("sort", sort)
                .getResultList());
//        }
//        Collections.sort(result, )
//        return result;

    }

    public List<?> findByJobGroup(JobGroup jobGroup, String sort, int limit, int offset) {
        List<List<?>> result = new ArrayList<>();
        int postSize = 0;
        for (Job job : jobGroup.getJobs()) {
            postSize += job.getPosts().size();
        }

        int startValue = Math.min(offset, postSize);
        int endValue = Math.min(offset + limit, postSize);

        if (sort.equals("count")) {
            return em.createQuery("select p, j, (select count(id) from BookMark group by post having post= :post) as count from Post p where p= :post")
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

    public List<Post> findAll() {
        return em.createQuery("select p from Post p", Post.class)
                .getResultList();
    }

    public BookMark getBookMark(User user, Post post) {
        return em.createQuery("select b from BookMark b where b.user= :user and b.post= :post", BookMark.class)
                .setParameter("user", user)
                .setParameter("post", post)
                .getSingleResult();
    }

    public List<?> getPostWithCount(Post post) {
        return em.createQuery("select p, (select count(id) from BookMark group by post having post= :post) as count from Post p where p= :post")
                .setParameter("post", post)
                .getResultList();
    }
}

package kooted.kooted.repository;

import kooted.kooted.model.Job;
import kooted.kooted.model.JobGroup;
import kooted.kooted.model.User;
import kooted.kooted.model.WorkingYear;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

@Repository
@RequiredArgsConstructor
public class JobRepository {

    @PersistenceContext
    private final EntityManager em;

    public void saveJobGroup (JobGroup jobGroup) {
        em.persist(jobGroup);
    }

    public JobGroup findJobGroup(Long id) {
        try {
            JobGroup jobGroup = em.find(JobGroup.class, id);
            if (jobGroup == null) {
                throw new IllegalStateException();
            }
            return jobGroup;
        } catch (IllegalStateException ex) {
            ex.printStackTrace();
            throw new IllegalStateException("JOB_GROUP_DOES_NOT_EXIST");
        }
    }

    public JobGroup findJobGroupByName(String name) {
        try {
            JobGroup jobGroup = em.createQuery("select j from JobGroup j where j.name = :name", JobGroup.class)
                    .setParameter("name", name)
                    .getSingleResult();
            if (jobGroup == null) {
                throw new IllegalStateException();
            }
            return jobGroup;
        } catch (IllegalStateException ex) {
            ex.printStackTrace();
            throw new IllegalStateException("JOB_GROUP_DOES_NOT_EXIST");
        }
    }

    public void saveJob (Job job) {
        em.persist(job);
    }

    public Job findOne(Long id) {
        try {
            Job job = em.find(Job.class, id);
            if (job == null) {
                throw new IllegalStateException();
            }
            return job;
        } catch (IllegalStateException ex) {
            ex.printStackTrace();
            throw new IllegalStateException("JOB_DOES_NOT_EXIST");
        }
    }

    public Job findJobByName(String name) {
        try {
            Job job = em.createQuery("select j from Job j where j.name = :name", Job.class)
                    .setParameter("name", name)
                    .getSingleResult();
            if (job == null) {
                throw new IllegalStateException();
            }
            return job;
        } catch (IllegalStateException ex) {
            ex.printStackTrace();
            throw new IllegalStateException("JOB_DOES_NOT_EXIST");
        }
    }

    public WorkingYear findWorkingYear(Long id) {
        try {
            WorkingYear workingYear = em.find(WorkingYear.class, id);
            if (workingYear == null) {
                throw new IllegalStateException();
            }
            return workingYear;
        } catch (IllegalStateException ex) {
            ex.printStackTrace();
            throw new IllegalStateException("WORKING_YEAR_DOES_NOT_EXIST");
        }
    }
}

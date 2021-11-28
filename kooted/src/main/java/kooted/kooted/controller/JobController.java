package kooted.kooted.controller;

import kooted.kooted.model.Job;
import kooted.kooted.model.JobGroup;
import kooted.kooted.repository.JobRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

@RequiredArgsConstructor
@RestController
@Transactional
@CrossOrigin
public class JobController {

    private final JobRepository jobRepository;

    @PostMapping("job-groups")
    public void createJobGroup(@RequestBody JobForm jobForm) throws NullPointerException {
        JobGroup jobGroup = new JobGroup();
        jobGroup.setName(jobForm.getJobGroupName());
        jobRepository.saveJobGroup(jobGroup);
    }

    @PostMapping("jobs")
    public void createJob(@RequestBody JobForm jobForm) {
        Job job = new Job();
        job.setName(jobForm.getJobName());
        job.setJobGroup(jobRepository.findJobGroup(jobForm.getJobGroupId()));
        jobRepository.saveJob(job);
    }
}

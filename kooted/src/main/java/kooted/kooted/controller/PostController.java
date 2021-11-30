package kooted.kooted.controller;

import kooted.kooted.model.Post;
import kooted.kooted.repository.CompanyRepository;
import kooted.kooted.repository.JobRepository;
import kooted.kooted.repository.PostRepository;
import kooted.kooted.service.PostService;
import lombok.RequiredArgsConstructor;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("posts")
@Transactional
@CrossOrigin
public class PostController {

    private final PostService postService;
    private final PostRepository postRepository;
    private final JobRepository jobRepository;
    private final CompanyRepository companyRepository;

    @PostMapping("")
    public void createPost(@RequestBody PostForm postForm) {
        Post post = new Post();
        post.setCompany(companyRepository.findOne(postForm.getCompany()));
        post.setContent(postForm.getContent());
        post.setCreated_at(LocalDateTime.now());
        post.setJob(jobRepository.findOne(postForm.getJob()));
        post.setApplicantReward(postForm.getApplicant_reward());
        post.setRecommenderReward(postForm.getRecommender_reward());
        post.setWorkingYear(postForm.getWorking_year());
        post.setDeadline(postForm.getDeadline());
        post.setUpdated_at(LocalDateTime.now());
        post.setTitle(postForm.getTitle());
        postService.createPost(post);
    }

    @GetMapping("/{postId}")
    public Post getPost(@PathVariable(value = "postId") Long id) {
        return postRepository.findOne(id);
    }

    @GetMapping("")
    public HashMap<String, Object> getByJob(
            @RequestParam(required = false) String job,
            @RequestParam(required = false) String job_group,
            @RequestParam(required = false) String sort,
            @RequestParam(required = false) Integer limit,
            @RequestParam(required = false) Integer offset) {
        HashMap<String, Object> result = new HashMap<>();
        System.out.println(limit);
        if (limit == null) {  limit = 8; }
        if (offset == null) { offset =  0; }
        if (sort == null) { sort = "created_at"; }

        if (job != null) {
            result.put("result", postRepository.findByJob(jobRepository.findJobByName(job), sort, limit, offset));
        } else if (job_group != null) {
            result.put("result", postRepository.findByJobGroup(jobRepository.findJobGroupByName(job_group), sort, limit, offset));
        } else {
            result.put("result", postRepository.findAll());
        }

        return result;
    }
}

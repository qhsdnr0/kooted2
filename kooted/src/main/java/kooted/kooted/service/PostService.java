package kooted.kooted.service;

import kooted.kooted.model.Job;
import kooted.kooted.model.Post;
import kooted.kooted.repository.CompanyRepository;
import kooted.kooted.repository.JobRepository;
import kooted.kooted.repository.PostRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
@RequiredArgsConstructor
public class PostService {

    private final PostRepository postRepository;
    private final CompanyRepository companyRepository;
    private final JobRepository jobRepository;

    public void createPost(Post post) {
        try {
            if (companyRepository.findOne(post.getCompany().getId()) == null) {
                throw new IllegalStateException();
            } else if (jobRepository.findOne(post.getJob().getId()) == null) {
                throw new IllegalStateException();
            }
            postRepository.save(post);
        } catch (IllegalStateException ex) {
            ex.printStackTrace();
        }
    }

    public Post getPost(Post post) {
        return postRepository.findOne(post.getId());
    }
}

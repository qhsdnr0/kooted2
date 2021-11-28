package kooted.kooted.service;

import kooted.kooted.model.Post;
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

    public void createPost(Post post) {
        postRepository.save(post);
    }

    public Post getPost(Post post) {
        return postRepository.findOne(post.getId());
    }
}

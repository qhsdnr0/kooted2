package kooted.kooted.controller;

import kooted.kooted.model.BookMark;
import kooted.kooted.model.Post;
import kooted.kooted.model.User;
import kooted.kooted.repository.PostRepository;
import kooted.kooted.repository.UserRepository;
import kooted.kooted.token.Token;
import lombok.RequiredArgsConstructor;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("")
@Transactional
@CrossOrigin
public class BookMarkController {

    private final PostRepository postRepository;
    private final UserRepository userRepository;
    private final Token access_token;

    @PostMapping("posts/{postId}/bookmarks")
    public void createBookMark(/*@RequestHeader("Authorization") String token,*/ @PathVariable("postId") Long id) {
//        User user = userRepository.findOne(access_token.decodeJwtToken(token));
        User user = userRepository.findOne(2L);
        Post post = postRepository.findOne(id);
        BookMark bookMark = new BookMark();
        bookMark.setPost(post);
        bookMark.setUser(user);
        userRepository.saveBookMark(bookMark);
    }

    @DeleteMapping("posts/{postId}/bookmarks")
    public void deleteBookMark(@RequestHeader("Authorization") String token, @PathVariable("postId") Long id) {
        User user = userRepository.findOne(access_token.decodeJwtToken(token));
        Post post = postRepository.findOne(id);
        BookMark bookMark = postRepository.getBookMark(user, post);
        userRepository.deleteBookMark(bookMark);
    }
}

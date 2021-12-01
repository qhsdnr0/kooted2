package kooted.kooted.controller;

import kooted.kooted.model.Application;
import kooted.kooted.model.Resume;
import kooted.kooted.model.User;
import kooted.kooted.repository.ApplicationRepository;
import kooted.kooted.repository.UserRepository;
import kooted.kooted.token.Token;
import lombok.RequiredArgsConstructor;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;

@RestController
@RequiredArgsConstructor
@RequestMapping("application")
@Transactional
@CrossOrigin
public class ApplicationController {

    private final ApplicationRepository applicationRepository;
    private final UserRepository userRepository;
    private final Token access_token;

    @PostMapping("")
    public void createApplication(@RequestHeader("Authorization") String token, @RequestBody ApplicationForm applicationForm) {
        User user = userRepository.findOne(access_token.decodeJwtToken(token));
        Resume resume = user.getResumes().get(applicationForm.getResume_number() - 1);
        Application application = new Application();
        application.setCreated_at(LocalDateTime.now());
        application.setRecommender(applicationForm.getRecommender());
        application.setUpdated_at(LocalDateTime.now());
        application.setResume(resume);
        application.setUser(user);
        applicationRepository.save(application);
    }
}

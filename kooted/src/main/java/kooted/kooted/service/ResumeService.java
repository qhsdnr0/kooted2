package kooted.kooted.service;

import kooted.kooted.model.Resume;
import kooted.kooted.model.User;
import kooted.kooted.repository.ResumeRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
@RequiredArgsConstructor
public class ResumeService {

    private final ResumeRepository resumeRepository;

    public void createResume(Resume resume) {
        resumeRepository.save(resume);
    }

    public Resume getResume(Resume resume) {
        return resumeRepository.findOne(resume.getId());
    }

    public List<Resume> getResumeByUser(User user) {
        return resumeRepository.findByUser(user);
    }
}

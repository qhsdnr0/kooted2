package kooted.kooted.controller;

import kooted.kooted.model.Career;
import kooted.kooted.model.Resume;
import kooted.kooted.model.Skill;
import kooted.kooted.model.User;
import kooted.kooted.repository.ResumeRepository;
import kooted.kooted.repository.UserRepository;
import kooted.kooted.service.ResumeService;
import kooted.kooted.token.Token;
import lombok.RequiredArgsConstructor;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.HashMap;

@RestController
@RequiredArgsConstructor
@RequestMapping("resumes")
@Transactional
@CrossOrigin
public class ResumeController {

    private final ResumeService resumeService;
    private final ResumeRepository resumeRepository;
    private final UserRepository userRepository;
    private final Token access_token;

    @PostMapping("")
    public void createResume(@RequestHeader("Authorization") String token, @RequestBody ResumeForm resumeForm) {
        Resume resume = new Resume();
        User user = userRepository.findOne(access_token.decodeJwtToken(token));

        resume.setCollege(resumeForm.getCollege());
        resume.setCreated_at(LocalDateTime.now());
        resume.setUpdated_at(LocalDateTime.now());
        resume.setUser(user);
        resume.setStatus(resumeForm.getStatus());
        resume.setTitle(resumeForm.getTitle());


        for (HashMap<String, Object> getCareer : resumeForm.getCareers()) {
            Career career = new Career();
            Object company_name = getCareer.get("company_name");
            Object duty = getCareer.get("duty");
            Object in_office = getCareer.get("in_office");
            Object date_of_joining = getCareer.get("date_of_joining");
            Object date_of_resigning = getCareer.get("date_of_resigning");

            career.setCompanyName((String) company_name);
            career.setDuty((String) duty);
            career.setInOffice((Boolean) in_office);
            career.setDateOfJoining(LocalDate.parse((String) date_of_joining, DateTimeFormatter.ISO_LOCAL_DATE));
            career.setDateOfResigning(LocalDate.parse((String) date_of_resigning, DateTimeFormatter.ISO_LOCAL_DATE));
            career.setResume(resume);
            resume.getCareers().add(career);
        }

        for (String skillName : resumeForm.getSkills()) {
            Skill skill = new Skill();
            skill.setName(skillName);
            skill.setResume(resume);
            resume.getSkills().add(skill);
        }
        resumeRepository.save(resume);

    }

    @GetMapping("/{resumeId}")
    public Resume getResume(@RequestHeader("Authorization") String token, @PathVariable("resumeId") Long resumeId) {
        User user = userRepository.findOne(access_token.decodeJwtToken(token));
        Resume resume = resumeRepository.findOne(resumeId);
        try {
            if (resume.getUser() != user) {
                throw new IllegalStateException();
            }
            return resume;
        } catch (IllegalStateException ex) {
            ex.printStackTrace();
            throw new IllegalStateException("FORBIDDEN_RESUME");
        }
    }

    @DeleteMapping("/{resumeId}")
    public void deleteResume(@RequestHeader("Authorization") String token, @PathVariable("resumeId") Long resumeId) {
        User user = userRepository.findOne(access_token.decodeJwtToken(token));
        Resume resume = resumeRepository.findOne(resumeId);
        try {
            if (resume.getUser() != user) {
                throw new IllegalStateException();
            }
            resumeRepository.deleteResume(resume);
        } catch (IllegalStateException ex) {
            ex.printStackTrace();
            throw new IllegalStateException("FORBIDDEN_RESUME");
        }
    }

    @PutMapping("/{resumeId}")
    public void updateResume(@RequestHeader("Authorization") String token, @PathVariable("resumeId") Long resumeId,
                               @RequestBody ResumeForm resumeForm) {
        User user = userRepository.findOne(access_token.decodeJwtToken(token));
        Resume resume = resumeRepository.findOne(resumeId);


        // careerController create
        try {
            if (resume.getUser() != user) {
                throw new IllegalStateException();
            }
            resume.setCollege(resumeForm.getCollege());
            resume.setUpdated_at(LocalDateTime.now());
            resume.setStatus(resumeForm.getStatus());
            resume.setTitle(resumeForm.getTitle());

            for (HashMap<String, Object> getCareer : resumeForm.getCareers()) {
                int index = resumeForm.getCareers().indexOf(getCareer);
                System.out.println(resume.getCareers().size());
                Career career = new Career();
                if (index >= resume.getCareers().size()) {
                    resume.getCareers().add(career);
                } else {
                    career = resume.getCareers().get(index);
                }

                Object company_name = getCareer.get("company_name");
                Object duty = getCareer.get("duty");
                Object in_office = getCareer.get("in_office");
                Object date_of_joining = getCareer.get("date_of_joining");
                Object date_of_resigning = getCareer.get("date_of_resigning");

                career.setCompanyName((String) company_name);
                career.setDuty((String) duty);
                career.setInOffice((Boolean) in_office);
                career.setDateOfJoining(LocalDate.parse((String) date_of_joining, DateTimeFormatter.ISO_LOCAL_DATE));
                career.setDateOfResigning(LocalDate.parse((String) date_of_resigning, DateTimeFormatter.ISO_LOCAL_DATE));
                career.setResume(resume);

            }

            for (String skillName : resumeForm.getSkills()) {
                int index = resumeForm.getSkills().indexOf(skillName);
                Skill skill = index < resume.getSkills().size() ? resume.getSkills().get(index) : new Skill();
                skill.setName(skillName);
                skill.setResume(resume);

            }

        } catch (IllegalStateException ex) {
            ex.printStackTrace();
            throw new IllegalStateException("FORBIDDEN_RESUME");
        }
    }
}

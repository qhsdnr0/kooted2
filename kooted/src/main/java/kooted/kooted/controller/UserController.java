package kooted.kooted.controller;

import kooted.kooted.model.*;
import kooted.kooted.repository.ApplicationRepository;
import kooted.kooted.repository.JobRepository;
import kooted.kooted.repository.UserRepository;
import kooted.kooted.service.UserService;
import kooted.kooted.token.Token;
import lombok.RequiredArgsConstructor;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import org.springframework.boot.json.JsonParser;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;

@RequiredArgsConstructor
@Transactional
@RestController
@RequestMapping("users")
@CrossOrigin
public class UserController {

    private final UserService userService;
    private final UserRepository userRepository;
    private final ApplicationRepository applicationRepository;
    private final JobRepository jobRepository;
    private final Token accessToken;

    @GetMapping("")
    public HashMap<String, Object> getUserInfo(@RequestHeader("Authorization") String token) {
        HashMap<String, Object> result = new HashMap<>();
        User user = userRepository.findOne(accessToken.decodeJwtToken(token));
        result.put("result", applicationRepository.findUserInfo(user));
        result.put("count", applicationRepository.findUserInfo(user).size());
        return result;
    }

    @GetMapping("/kakao")
    public HashMap<String, Object> getOrCreateUser(@RequestHeader("Authorization") String token) {
        HashMap<String, Object> result = new HashMap<>();

        try {
            String userInfo = userService.createKakaoUser(token);
            JSONParser jsonParser = new JSONParser();
            JSONObject jsonObject = (JSONObject) jsonParser.parse(userInfo);
            Long kakaoId = (Long) jsonObject.get("id");
            JSONObject kakao_account = (JSONObject) jsonObject.get("kakao_account");
            JSONObject profile = (JSONObject) kakao_account.get("profile");
            String userEmail = (String) kakao_account.get("email");
            String userName = (String) profile.get("nickname");

            if (userRepository.findByKakaoId(kakaoId).isEmpty()) {
                User newUser = new User();
                newUser.setKakaoId(kakaoId);
                newUser.setName(userName);
                newUser.setEmail(userEmail);
                userService.createUser(newUser);
                result.put("message", "NOT_ENOUGH_INFORMATION");
                result.put("access_token", accessToken.makeJwtToken(newUser));
            } else {
                User user = userRepository.findByKakaoId(kakaoId).get(0);
                result.put("message", "SUCCESS");
                result.put("access_token", accessToken.makeJwtToken(user));
            }
            return result;

        } catch (ParseException ex) {
            ex.printStackTrace();
            throw new Error("USER_DOES_NOT_EXIST");
        }
    }

    @PutMapping("")
    public HashMap<String, Object> updateUser(@RequestHeader("Authorization") String token, @RequestBody UserForm userForm) {
        HashMap<String, Object> result = new HashMap<>();
        try {
            User user = userRepository.findOne(accessToken.decodeJwtToken(token));
            Career career = new Career();
            AddInfo addInfo = new AddInfo(userForm.getCollege(), userForm.getSalary(), userForm.getIntroduction(), userForm.getCompany_name(),
                    userForm.getIn_office(), userForm.getDuty(), userForm.getDate_of_joining(), userForm.getDate_of_resigning());
            UserWorkingYear userWorkingYear = new UserWorkingYear();

            if (!user.getUserWorkingYears().isEmpty()) {
                userWorkingYear = user.getUserWorkingYears().get(0);
            }

            userWorkingYear.setUser(user);
            userWorkingYear.setJob(jobRepository.findOne(userForm.getJob_id()));
            userWorkingYear.setWorkingYear(jobRepository.findWorkingYear(userForm.getWorking_year()));
            user.setAddInfo(addInfo);
            user.getUserWorkingYears().add(userWorkingYear);
            result.put("message", "SUCCESS");
            return result;
        } catch (IllegalStateException ex) {
            ex.printStackTrace();
            throw new IllegalStateException("INVALID_INPUT");
        }
    }

    @GetMapping("salary")
    public HashMap<String, Object> getSalaryAverage(@RequestParam(required = false) String job, @RequestParam(required = false) String job_group) {
        HashMap<String, Object> result = new HashMap<>();
        if (job_group != null) {
            result.put("averageSalary", userRepository.getAverageSalaryByJobGroup(jobRepository.findJobGroupByName(job_group)));

        } else if (job != null) {
            result.put("averageSalary", userRepository.getAverageSalaryByJob(jobRepository.findJobByName(job)));
        }
        return result;
    }
}

package kooted.kooted.controller;

import kooted.kooted.model.AddInfo;
import kooted.kooted.model.User;
import kooted.kooted.model.UserWorkingYear;
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
    private final JobRepository jobRepository;
    private final Token accessToken;

    @GetMapping("/hello")
    public User a() {
        return userRepository.findOne(Long.parseLong("2"));
    }

    @GetMapping("/kakao")
    public HashMap<String, Object> getOrCreateUser(@RequestHeader("Authorization") String token) {
        HashMap<String, Object> result = new HashMap<>();
        System.out.println(token);

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

    @PutMapping("/kakao")
    public HashMap<String, Object> updateUser(@RequestHeader("Authorization") String token, @RequestBody UserForm userForm) {
        HashMap<String, Object> result = new HashMap<>();
        try {
            User user = userRepository.findOne(accessToken.decodeJwtToken(token));
            AddInfo addInfo = new AddInfo(userForm.getCollege(), userForm.getSalary(), userForm.getIntroduction(), userForm.getCompanyName(),
                                          userForm.getInOffice(), userForm.getDuty(), userForm.getDateOfJoining(), userForm.getDateOfResigning());
            UserWorkingYear userWorkingYear = new UserWorkingYear();
            userWorkingYear.setUser(user);
            userWorkingYear.setJob(jobRepository.findOne(userForm.getJobId()));
            userWorkingYear.setWorkingYear(jobRepository.findWorkingYear(userForm.getWorkingYearId()));
            user.setAddInfo(addInfo);
            user.getUserWorkingYears().add(userWorkingYear);
            userRepository.saveUserWorkingYear(userWorkingYear);
            result.put("message", "SUCCESS");
            return result;
        } catch (IllegalStateException ex) {
            ex.printStackTrace();
            throw new IllegalStateException("INVALID_INPUT");
        }
    }
}

package kooted.kooted.service;

import com.fasterxml.jackson.annotation.JsonProperty;
import kooted.kooted.model.User;
import kooted.kooted.model.UserWorkingYear;
import kooted.kooted.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;

import java.util.Map;

@Service
@RequiredArgsConstructor
@Transactional
public class UserService {

    private final RestTemplate restTemplate;
    private final UserRepository userRepository;

    public void createUser(User user) {
        userRepository.save(user);
    }

    public User getUser(User user) {
        return userRepository.findOne(user.getId());
    }

    public String createKakaoUser(String token) {
        HttpHeaders headers = new HttpHeaders();
        headers.set("Authorization", "Bearer " + token);
        headers.setContentType(MediaType.APPLICATION_JSON);
        HttpEntity<Object> entity = new HttpEntity<>("parameters", headers);

        try {
            ResponseEntity<String> response = restTemplate.postForEntity("https://kapi.kakao.com/v2/user/me", entity, String.class);
            return response.getBody();
        } catch (RestClientException ex) {
            ex.printStackTrace();
            throw new IllegalStateException("Unauthorized");
        }

    }
}

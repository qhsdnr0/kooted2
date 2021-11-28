package kooted.kooted.service;

import kooted.kooted.model.Application;
import kooted.kooted.repository.ApplicationRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
@RequiredArgsConstructor
public class ApplicationService {

    private final ApplicationRepository applicationRepository;

    public void createApplication(Application application) {
        applicationRepository.save(application);
    }

    public Application getApplication(Application application) {
        return applicationRepository.findOne(application.getId());
    }
}

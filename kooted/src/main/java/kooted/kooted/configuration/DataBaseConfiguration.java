package kooted.kooted.configuration;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

@Configuration
public class DataBaseConfiguration {

    @PersistenceContext
    private EntityManager entityManager;

}

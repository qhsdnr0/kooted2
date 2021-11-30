package kooted.kooted;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import javax.persistence.EntityManager;

@SpringBootApplication
public class KootedApplication {

	public static void main(String[] args) {
		SpringApplication.run(KootedApplication.class, args);
	}


}

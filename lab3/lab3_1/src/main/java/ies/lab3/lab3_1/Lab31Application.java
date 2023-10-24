package ies.lab3.lab3_1;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.transaction.annotation.EnableTransactionManagement;

@SpringBootApplication
// @EnableJpaRepositories(basePackages = {"repositories"})
// @EnableTransactionManagement
// @EntityScan(basePackages = {"entities"})
public class Lab31Application {

	public static void main(String[] args) {
		SpringApplication.run(Lab31Application.class, args);
	}

}

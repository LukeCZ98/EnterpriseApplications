package unical.informatica.it.enterpriseapplicationbackend;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class BackendApplication {

	public static void main(String[] args) {
		SpringApplication.run(BackendApplication.class, args);
	}
    //INCLUDERE NELLA RESPONSE DEL LOGIN ANCHE IL ROLE DI ADMIN
}

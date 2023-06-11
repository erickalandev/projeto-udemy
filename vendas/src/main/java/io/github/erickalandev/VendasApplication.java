package io.github.erickalandev;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@SpringBootApplication
@RestController
public class VendasApplication {

	@Value("${spring.application.name}")
	public String applicationName;
	
	public static void main(String[] args) {
		SpringApplication.run(VendasApplication.class, args);
	}
	
	@GetMapping("/hello")
	public String helloWorld() {
		return applicationName;
	}
}

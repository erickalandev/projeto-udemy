package io.github.erickalandev;

import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;

@Configuration
@Profile("development")
public class HelloWorldConfiguration {
	
	@Bean
	public CommandLineRunner execute() {
		return args -> {
			System.out.println("RODANDO A CONFIGURACAO DE DESENVOLVIMENTO");
		};
	}
}

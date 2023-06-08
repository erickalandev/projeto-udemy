package io.github.erickalandev;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@SpringBootApplication
/*
 * @ComponentScan
 * mais utilizado quando quero usar a aplicacao de outra pessoa, fora da MINHA aplicacao
 * no caso a minha aplicacao eh o io.github.erickalandev
 * e o da pessoa com.bibliotecaexterna.projeto
 */
@ComponentScan(
							basePackages = 
							{
									"com.bibliotecaexterna.projeto"
							}
						)
@RestController
public class VendasApplication {

	@Autowired
	@Qualifier("applicationName")
	public String applicationName;
	
	public static void main(String[] args) {
		SpringApplication.run(VendasApplication.class, args);
	}
	
	@GetMapping("/hello")
	public String helloWorld() {
		return applicationName;
	}
}

package io.github.erickalandev;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;

@SpringBootApplication
public class VendasApplication extends SpringBootServletInitializer {

	public static void main(String[] args) {
		SpringApplication.run(VendasApplication.class, args);
	}
	
	/*obs.: o SpringBootServletInitializer do tomcat, faz com que o sistema vire uma
	 * aplicacao web gerando um war. 
	 * Deixando de ser uma aplicacao standalone onde geravamos um jar.
	 * */

}

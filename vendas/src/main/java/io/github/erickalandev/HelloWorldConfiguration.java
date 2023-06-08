package io.github.erickalandev;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class HelloWorldConfiguration {
	
	@Bean(name = "applicationName")
	public String applicationName() {
		return "Hello World";
	}
}

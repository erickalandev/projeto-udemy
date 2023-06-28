package io.github.erickalandev.config;

import java.util.Locale;

import org.springframework.context.MessageSource;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.support.ReloadableResourceBundleMessageSource;
import org.springframework.validation.beanvalidation.LocalValidatorFactoryBean;

@Configuration
public class InternacionalizacaoConfig {

	@Bean
	public MessageSource messageSource() { // messageSource e o cara que procura a fonte de messagem que e o messages.properties
		ReloadableResourceBundleMessageSource messageSource = new ReloadableResourceBundleMessageSource();
		/* qual e o arquivo que vai carregar as mensagens la do properties, 
		*utilizando o classpath diz qual vai ser o arquivo, 
		*entao vc coloca o nome do arquivo, no caso o "messages"
		*/
		messageSource.setBasename("classpath:messages");
		messageSource.setDefaultEncoding("ISO-8859-1");//codificacao para aceitar pontuacao
		messageSource.setDefaultLocale(Locale.getDefault());
		return messageSource;
	}
	
	@Bean
	/*
	 * e responsavel por fazer a interpolacao. Vai pegar o messageSource, ou seja as mensagens
	 * escritas no messages.properties e vai passar para os caras que tem a { nome.do.campo }
	*/
	public LocalValidatorFactoryBean validatorFactoryBean() {
		LocalValidatorFactoryBean bean = new LocalValidatorFactoryBean();
		bean.setValidationMessageSource(messageSource()); //aqui e procurando as mensagens atraves do metodo criado acima
		return bean;
	}
	
}

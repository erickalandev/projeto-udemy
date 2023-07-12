package io.github.erickalandev.localizacao;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import io.github.erickalandev.localizacao.service.CidadeService;

@SpringBootApplication
public class LocalizacaoApplication implements CommandLineRunner {
	
	@Autowired
	private CidadeService cidadeService;
	
	@Override
	public void run(String... args) throws Exception {
//		cidadeService.listaNomes();
		cidadeService.listaNomesSqlNativoProjection();
	}
	
	public static void main(String[] args) {
		SpringApplication.run(LocalizacaoApplication.class, args);
	}

}

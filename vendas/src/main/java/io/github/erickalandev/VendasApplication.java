package io.github.erickalandev;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import io.github.erickalandev.domain.entity.Cliente;
import io.github.erickalandev.domain.repository.Clientes;

@SpringBootApplication
public class VendasApplication {
	
	@Autowired
	private Clientes clientes;
	
	@Bean
	public CommandLineRunner execute() {
		return args -> {
			clientes.salvar(new Cliente("Erick Alan"));
			clientes.salvar(new Cliente("Erick Alan2"));
			clientes.salvar(new Cliente("Erick Alan3"));
			clientes.salvar(new Cliente("Erick Alan4"));
			
			List<Cliente> todosClientes = clientes.obterTodos();
			
			todosClientes.forEach(System.out::println);
		};
	}

	public static void main(String[] args) {
		SpringApplication.run(VendasApplication.class, args);
	}

}

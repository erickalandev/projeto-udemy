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
			//CREATE
			clientes.salvar(new Cliente("teste 1"));
			clientes.salvar(new Cliente("teste 2"));
			clientes.salvar(new Cliente("teste 3"));
			clientes.salvar(new Cliente("teste 3"));
			clientes.salvar(new Cliente("teste 3"));
			clientes.salvar(new Cliente("teste 4"));
			
			System.out.println("//SELECT");
			clientes.obterTodos().forEach(System.out::println);
			System.out.println("_____________________________________________");
			
			System.out.println("//UPDATE");
			System.out.println(clientes.atualizar(new Cliente(2,"teste atualizado")).toString());
			System.out.println("_____________________________________________");
			
			System.out.println("//DELETE");
			clientes.deletar(3);
			clientes.obterTodos().forEach(System.out::println);
			System.out.println("_____________________________________________");
			
			System.out.println("//SELECT_BY_NAME");
			clientes.obterTodosPorNome("3").forEach(System.out::println);
			System.out.println("_____________________________________________");
			
			System.out.println("//DELETE_ALL");
			clientes.obterTodos().forEach(c -> clientes.deletar(c.getId()));
			if(clientes.obterTodos().isEmpty()) {
				System.out.println("Todos deletados!");
			}
			System.out.println("_____________________________________________");
		};
	}

	public static void main(String[] args) {
		SpringApplication.run(VendasApplication.class, args);
	}

}

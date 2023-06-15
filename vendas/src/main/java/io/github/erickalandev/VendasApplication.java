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
			clientes.save(new Cliente("teste 1"));
			clientes.save(new Cliente("teste 2"));
			clientes.save(new Cliente("teste 3"));
			clientes.save(new Cliente("teste 3"));
			clientes.save(new Cliente("teste 3"));
			clientes.save(new Cliente("teste 4"));
			
			System.out.println("//SELECT");
			clientes.findAll().forEach(System.out::println);
			System.out.println("_____________________________________________");
			
			System.out.println("//UPDATE");
			System.out.println(clientes.save(new Cliente(2,"teste atualizado")).toString());
			System.out.println("_____________________________________________");
			
			System.out.println("//DELETE");
			clientes.deleteById(3);
			clientes.findAll().forEach(System.out::println);
			System.out.println("_____________________________________________");
			
			System.out.println("//SELECT_BY_NAME");
			clientes.findByNomeLike("3").forEach(System.out::println);
			System.out.println("_____________________________________________");
			
			System.out.println("//EXISTS_BY_NAME");
			System.out.println(clientes.existsByNome("teste 3"));
			System.out.println("_____________________________________________");
			
			System.out.println("//DELETE_ALL");
			clientes.deleteAll();
			if(clientes.findAll().isEmpty()) {
				System.out.println("Todos deletados!");
			}
			System.out.println("_____________________________________________");
		};
	}

	public static void main(String[] args) {
		SpringApplication.run(VendasApplication.class, args);
	}

}

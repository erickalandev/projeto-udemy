package io.github.erickalandev;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import io.github.erickalandev.domain.entity.Cliente;
import io.github.erickalandev.domain.entity.Pedido;
import io.github.erickalandev.domain.repository.Clientes;
import io.github.erickalandev.domain.repository.Pedidos;

@SpringBootApplication
public class VendasApplication {
	
	@Autowired
	private Clientes clientes;

	@Autowired
	private Pedidos pedidos;
	
	@Bean
	public CommandLineRunner execute() {
		return args -> {
			//CREATE
			Cliente c = new Cliente("Fulano");
			clientes.save(c);
			
			Pedido p = new Pedido();
			p.setCliente(c);
			p.setDataPedido( LocalDate.now() );
			p.setTotal(BigDecimal.valueOf(100.00));
			
			pedidos.save(p);
			
			System.out.println(clientes.findClienteFetchPedidos(c.getId()).toString());
			
			pedidos.findByCliente(c).forEach(System.out::println);
			
		};
	}

	public static void main(String[] args) {
		SpringApplication.run(VendasApplication.class, args);
	}

}

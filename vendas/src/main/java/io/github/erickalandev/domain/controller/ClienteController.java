package io.github.erickalandev.domain.controller;

import java.util.List;
import java.util.Optional;

import org.springframework.data.domain.Example;
import org.springframework.data.domain.ExampleMatcher;
import org.springframework.data.domain.ExampleMatcher.StringMatcher;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import io.github.erickalandev.domain.entity.Cliente;
import io.github.erickalandev.domain.repository.Clientes;

@Controller
public class ClienteController {
	
	private Clientes clientes;
	
	public ClienteController(Clientes clientes) {
		this.clientes = clientes;
	}
	
	@GetMapping("/api/clientes/{id}")
	@ResponseBody
	public ResponseEntity getClienteById(@PathVariable Integer id) {
		Optional<Cliente> c = clientes.findById(id);
		if(c.isPresent()) {
			return ResponseEntity.ok(c);
		}
		return ResponseEntity.notFound().build();
	}
	
	@PostMapping("/api/clientes")
	@ResponseBody
	public ResponseEntity<Cliente> save(@RequestBody Cliente cliente) {
		Cliente clienteSalvo =clientes.save(cliente);
		return ResponseEntity.ok(clienteSalvo);
	}
	
	@DeleteMapping("/api/clientes/{id}")
	@ResponseBody
	public ResponseEntity delete(@PathVariable Integer id) {
		if(clientes.findById(id).isPresent()) {
			clientes.deleteById(id);
			return ResponseEntity.noContent().build();
		}
		return ResponseEntity.notFound().build();
	}
	
	@PutMapping("/api/clientes/{id}")
	@ResponseBody
	public ResponseEntity put(	@PathVariable Integer id,
										@RequestBody Cliente cliente) {
		return clientes.findById(id)
				.map(clienteExistente -> {
					cliente.setId(clienteExistente.getId());
					clientes.save(cliente);
					return ResponseEntity.noContent().build();
				})
				.orElseGet( () -> ResponseEntity.notFound().build() );
	}
	
	@GetMapping("api/clientes")
	@ResponseBody
	public ResponseEntity find(Cliente filter) {
		ExampleMatcher matcher = ExampleMatcher //configuracao de pesquisa
											.matching() //encontrar clientes
											.withIgnoreCase() //ignora caixa alto/baixa na pesquisa
											.withStringMatcher(StringMatcher.CONTAINING); //faz a consultas igual a do like %info%
		Example example = Example.of(filter, matcher);
		List<Cliente> lista = clientes.findAll(example);
		
		return ResponseEntity.ok(lista);
	}

}

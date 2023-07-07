package io.github.erickalandev.rest.controller;

import java.util.List;

import javax.validation.Valid;

import org.springframework.data.domain.Example;
import org.springframework.data.domain.ExampleMatcher;
import org.springframework.data.domain.ExampleMatcher.StringMatcher;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

import io.github.erickalandev.domain.entity.Cliente;
import io.github.erickalandev.domain.repository.Clientes;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;

@RestController // nao precisa mais usar toda vez a anotation @ResponseBody nas APIs
@RequestMapping("/api/clientes")
@Api("Api Clientes")
public class ClienteController {

	private Clientes clientes;

	public ClienteController(Clientes clientes) {
		this.clientes = clientes;
	}

	@GetMapping("/{id}")
	@ApiOperation("Obter detalhes de clientes")
	@ApiResponses({
					@ApiResponse(code = 200, message = "cliente encontrado"),
					@ApiResponse(code = 404, message = "cliente nao encontrado")
				 })
	public Cliente getClienteById(@PathVariable @ApiParam("id do cliente") Integer id) {
		return clientes.findById(id)
				.orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Cliente não encontrado"));
	}

	@PostMapping
	@ResponseStatus(HttpStatus.CREATED)
	@ApiOperation("salvar novo cliente")
	@ApiResponses({
					@ApiResponse(code = 201, message = "cliente salvo com sucesso"),
					@ApiResponse(code = 400, message = "Erro de validacao")
				 })
	public Cliente save(@RequestBody @Valid Cliente cliente) {
		return clientes.save(cliente);
	}

	@DeleteMapping("/{id}")
	@ResponseStatus(HttpStatus.NO_CONTENT)
	@ApiOperation("deletar cliente")
	@ApiResponses({
					@ApiResponse(code = 200, message = "cliente deletado!"),
					@ApiResponse(code = 404, message = "cliente nao encontrado")
				 })
	public void delete(@PathVariable Integer id) {
		clientes.findById(id).map(cliente -> {
			clientes.delete(cliente);
			return cliente;
		}).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Cliente nao encontrado!"));
	}

	@PutMapping("/{id}")
	@ResponseStatus(HttpStatus.NO_CONTENT)
	public void put(@PathVariable Integer id, @RequestBody @Valid  Cliente cliente) {
		clientes.findById(id).map(clienteExistente -> {
			cliente.setId(clienteExistente.getId());
			return clientes.save(cliente);
		}).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Cliente nao encontrado"));
	}

	@GetMapping
	public List<Cliente> find(Cliente filter) {
		ExampleMatcher matcher = ExampleMatcher // configuracao de pesquisa
				.matching() // encontrar clientes
				.withIgnoreCase() // ignora caixa alto/baixa na pesquisa
				.withStringMatcher(StringMatcher.CONTAINING); // faz a consultas igual a do like %info%
		Example example = Example.of(filter, matcher);
		return clientes.findAll(example);
	}

}

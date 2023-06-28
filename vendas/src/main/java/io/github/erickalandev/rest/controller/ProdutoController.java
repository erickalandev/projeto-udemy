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

import io.github.erickalandev.domain.entity.Produto;
import io.github.erickalandev.domain.repository.Produtos;

@RestController
@RequestMapping("api/produtos")
public class ProdutoController {

	private Produtos produtos;

	public ProdutoController(Produtos produtos) {
		this.produtos = produtos;
	}

	@GetMapping("/{id}")
	public Produto getByIdProdutos(@PathVariable Integer id) {
		return produtos.findById(id)
				.orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Produto nao encontrado!"));
	}

	@PostMapping
	@ResponseStatus(value = HttpStatus.CREATED)
	public Produto save(@RequestBody @Valid Produto produto) {
		return produtos.save(produto);
	}

	@DeleteMapping("/{id}")
	@ResponseStatus(value = HttpStatus.NO_CONTENT)
	public void delete(@PathVariable Integer id) {
		produtos.findById(id).map(p -> {
			produtos.delete(p);
			return p;
		}).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Produto nao encontrado!"));
	}

	@PutMapping("/{id}")
	@ResponseStatus(value = HttpStatus.NO_CONTENT)
	public void put(@RequestBody @Valid Produto produto, @PathVariable Integer id) {
		produtos.findById(id).map(produtoEncontrado -> {
			produto.setId(produtoEncontrado.getId());
			return produtos.save(produto);
		}).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Produto nao encontrado!"));
	}

	@GetMapping
	public List<Produto> getAll(Produto filter) {
		ExampleMatcher matcher = ExampleMatcher.matching().withIgnoreCase().withStringMatcher(StringMatcher.CONTAINING);
		Example<Produto> example = Example.of(filter, matcher);
		return produtos.findAll(example);
	}

}

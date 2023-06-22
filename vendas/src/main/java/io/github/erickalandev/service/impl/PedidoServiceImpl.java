package io.github.erickalandev.service.impl;

import org.springframework.stereotype.Service;

import io.github.erickalandev.domain.repository.Pedidos;
import io.github.erickalandev.service.PedidoService;

@Service
public class PedidoServiceImpl implements PedidoService {
	
	private Pedidos repository;
	
	public PedidoServiceImpl(Pedidos repository) {
		this.repository = repository;
	}
}

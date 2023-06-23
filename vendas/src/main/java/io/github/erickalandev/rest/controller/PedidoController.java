package io.github.erickalandev.rest.controller;

import static org.springframework.http.HttpStatus.*;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import io.github.erickalandev.rest.dto.PedidoDTO;
import io.github.erickalandev.service.PedidoService;


@RestController
@RequestMapping("/api/pedidos")
public class PedidoController {

	private PedidoService pedidoService;
	
	public PedidoController(PedidoService pedidoService) {
		this.pedidoService = pedidoService;
	}
	
	@PostMapping
	@ResponseStatus(CREATED)
	public Integer save( @RequestBody PedidoDTO pedidoDto) {
		return pedidoService.save(pedidoDto).getId();
	}
}

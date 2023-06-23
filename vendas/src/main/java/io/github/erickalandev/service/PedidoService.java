package io.github.erickalandev.service;

import io.github.erickalandev.domain.entity.Pedido;
import io.github.erickalandev.rest.dto.PedidoDTO;

public interface PedidoService {
	public Pedido save(PedidoDTO pedidoDto);
}

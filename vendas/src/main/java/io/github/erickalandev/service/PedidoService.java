package io.github.erickalandev.service;

import java.util.Optional;

import io.github.erickalandev.domain.entity.Pedido;
import io.github.erickalandev.domain.enums.StatusPedido;
import io.github.erickalandev.rest.dto.PedidoDTO;

public interface PedidoService {
	public Pedido save(PedidoDTO pedidoDto);
	Optional<Pedido> obterPedidoCompleto(Integer id);
	void AtualizarStatus(Integer id, StatusPedido status);
}

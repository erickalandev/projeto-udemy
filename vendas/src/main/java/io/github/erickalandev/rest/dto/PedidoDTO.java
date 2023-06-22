package io.github.erickalandev.rest.dto;

import java.math.BigDecimal;
import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class PedidoDTO {

	/*
	 * { "cliente": 1, "total": 100, "items": [ { "produto": 1, "quantidade": 1 } ] }
	 */
	private Integer cliente;
	private BigDecimal total;
	private List<ItemPedidoDto> items;
}

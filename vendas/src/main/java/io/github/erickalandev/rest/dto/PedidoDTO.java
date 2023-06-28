package io.github.erickalandev.rest.dto;

import java.math.BigDecimal;
import java.util.List;

import javax.validation.constraints.NotNull;

import io.github.erickalandev.validation.constraintvalid.NotEmptyList;
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
	
	@NotNull( message = "Informe o codigo do cliente no pedido")
	private Integer cliente;
	
	@NotNull( message = "Informe o total do pedido")
	private BigDecimal total;
	
	@NotEmptyList
	private List<ItemPedidoDto> items;
}

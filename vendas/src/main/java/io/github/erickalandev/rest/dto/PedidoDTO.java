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

	@NotNull( message = "{campo.codigo-cliente.obrigatorio}")
	private Integer cliente;
	
	@NotNull( message = "{campo.total-pedido.obrigatorio}")
	private BigDecimal total;
	
	@NotEmptyList( message = "{campo.items-pedido.obrigatorio}")
	private List<ItemPedidoDTO> items;
}

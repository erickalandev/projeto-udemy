package io.github.erickalandev.rest.dto;

import java.math.BigDecimal;
import java.util.List;

import io.github.erickalandev.domain.enums.StatusPedido;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class InformacoesPedidoDTO {
	
	private Integer codigo;
	private String cpf;
	private String nomeCliente;
	private BigDecimal total;
	private String dataPedido;
	private StatusPedido status;
	private List<InformacoesItemPedidoDTO> items;
	

}

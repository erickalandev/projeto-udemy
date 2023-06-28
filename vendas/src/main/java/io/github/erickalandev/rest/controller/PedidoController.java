package io.github.erickalandev.rest.controller;

import static org.springframework.http.HttpStatus.CREATED;

import java.time.format.DateTimeFormatter;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.http.HttpStatus;
import org.springframework.util.CollectionUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import io.github.erickalandev.domain.entity.ItemPedido;
import io.github.erickalandev.domain.entity.Pedido;
import io.github.erickalandev.domain.enums.StatusPedido;
import io.github.erickalandev.exception.RegraNegocioException;
import io.github.erickalandev.rest.dto.AtualizacaoStatusPedidoDTO;
import io.github.erickalandev.rest.dto.InformacoesItemPedidoDTO;
import io.github.erickalandev.rest.dto.InformacoesPedidoDTO;
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
	
	@GetMapping("/{id}")
	public InformacoesPedidoDTO getById( @PathVariable Integer id) {
		return pedidoService	.obterPedidoCompleto(id)
									.map( p -> converter(p) )
									.orElseThrow( () -> new RegraNegocioException("Pedido nao encontrado"));
	}
	
	private InformacoesPedidoDTO converter( Pedido pedido) {
		return InformacoesPedidoDTO	.builder()
										.codigo(pedido.getId())
										.dataPedido(pedido.getDataPedido().format(DateTimeFormatter.ofPattern("dd/MM/yyyy")))
										.cpf(pedido.getCliente().getCpf())
										.nomeCliente(pedido.getCliente().getNome())
										.total(pedido.getTotal())
										.status(pedido.getStatus())
										.items(converter(pedido.getItens()))
										.build();						
	}
	
	private List<InformacoesItemPedidoDTO> converter( List<ItemPedido> itens) {
		if (CollectionUtils.isEmpty(itens)) {
			return Collections.emptyList();
		}
		
		return itens.stream()
				.map(
						item -> InformacoesItemPedidoDTO.builder()
								.descricaoProduto(item.getProduto().getDescricao())
								.precoUnitario(item.getProduto().getPreco())
								.quantidade(item.getQuantidade())
								.build()
						).collect(Collectors.toList());
	}
	
	@PatchMapping("/{id}")
	@ResponseStatus(HttpStatus.NO_CONTENT)
	public void updateStatus( @PathVariable Integer id, @RequestBody AtualizacaoStatusPedidoDTO status) {
		pedidoService.AtualizarStatus(id, StatusPedido.valueOf(status.getNovoStatus()));
	}
}

package io.github.erickalandev.service.impl;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import io.github.erickalandev.domain.entity.Cliente;
import io.github.erickalandev.domain.entity.ItemPedido;
import io.github.erickalandev.domain.entity.Pedido;
import io.github.erickalandev.domain.entity.Produto;
import io.github.erickalandev.domain.enums.StatusPedido;
import io.github.erickalandev.domain.repository.Clientes;
import io.github.erickalandev.domain.repository.ItensPedidos;
import io.github.erickalandev.domain.repository.Pedidos;
import io.github.erickalandev.domain.repository.Produtos;
import io.github.erickalandev.exception.PedidoNotFoundException;
import io.github.erickalandev.exception.RegraNegocioException;
import io.github.erickalandev.rest.dto.ItemPedidoDto;
import io.github.erickalandev.rest.dto.PedidoDTO;
import io.github.erickalandev.service.PedidoService;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor //anotacao do lombok onde nao precisa colocar as injecoes de dependencias
@Service
public class PedidoServiceImpl implements PedidoService {

	private final Pedidos repository;
	private final Clientes clientesRepository;
	private final Produtos produtosRepository;
	private final ItensPedidos itensPedidosRepository;


	@Override
	@Transactional
	public Pedido save(PedidoDTO dto) {
		Cliente cliente = clientesRepository.findById(dto.getCliente())
				.orElseThrow( () -> new RegraNegocioException("Usuario nao encontrado"));
		
		Pedido pedido = new Pedido();
		pedido.setCliente(cliente);
		pedido.setDataPedido(LocalDate.now());
		pedido.setStatus(StatusPedido.REALIZADO);
		pedido.setTotal(dto.getTotal());
		
		List<ItemPedido> itens = converterItensPedidos(pedido, dto.getItems());
		
		repository.save(pedido);
		itensPedidosRepository.saveAll(itens);
		pedido.setItens(itens);
		return pedido;
	}
	
	private List<ItemPedido> converterItensPedidos(Pedido pedido, List<ItemPedidoDto> items) {
		if(items.isEmpty()) {
			throw new RegraNegocioException("Itens dos pedidos nao realizados");
		}
		return items.stream()
						.map(itemEncontrado -> {
							Produto produto = produtosRepository.findById(itemEncontrado.getProduto())
									.orElseThrow( () -> new RegraNegocioException("Codigo do produto nao encontrado"));
							
							ItemPedido itemPedidos = new ItemPedido();
							itemPedidos.setPedido(pedido);
							itemPedidos.setQuantidade(itemEncontrado.getQuantidade());
							itemPedidos.setProduto(produto);
					
							return itemPedidos;
						}).collect(Collectors.toList());
	}

	
	
	@Override
	public Optional<Pedido> obterPedidoCompleto(Integer id) {
		return repository.findByIdFetchItems(id);
	}

	@Override
	public void AtualizarStatus(Integer id, StatusPedido status) {
		repository.findById(id).map( pedido -> {
			pedido.setStatus(status);
			return repository.save(pedido);
		}).orElseThrow( () -> new PedidoNotFoundException());
	}
	
}

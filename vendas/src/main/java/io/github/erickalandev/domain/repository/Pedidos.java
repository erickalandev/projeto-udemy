package io.github.erickalandev.domain.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import io.github.erickalandev.domain.entity.Cliente;
import io.github.erickalandev.domain.entity.Pedido;

public interface Pedidos extends JpaRepository<Pedido, Integer> {

	List<Pedido>findByCliente(Cliente cliente);
}

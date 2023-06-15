package io.github.erickalandev.domain.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import io.github.erickalandev.domain.entity.ItemPedido;

public interface ItensPedidos extends JpaRepository<ItemPedido, Integer> {

}

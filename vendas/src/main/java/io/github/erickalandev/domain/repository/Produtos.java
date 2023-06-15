package io.github.erickalandev.domain.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import io.github.erickalandev.domain.entity.Produto;

public interface Produtos extends JpaRepository<Produto, Integer> {

}

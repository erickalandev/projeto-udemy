package io.github.erickalandev.domain.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import io.github.erickalandev.domain.entity.Cliente;

public interface Clientes extends JpaRepository<Cliente, Integer> {
	
	@Query("select c from Cliente c where c.nome like %:nome%")//consulta JPQL
	List<Cliente> findByNomeLike(String nome);
	
	@Query(value = "select * from cliente c where c.nome like %:nome%", nativeQuery=true)//consulta SQL nativo
	void deleteByNome( @Param("nome") String nome);
	
	Boolean existsByNome(String nome);
}

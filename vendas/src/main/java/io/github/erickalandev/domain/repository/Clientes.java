package io.github.erickalandev.domain.repository;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import io.github.erickalandev.domain.entity.Cliente;

@Repository
public class Clientes {

	@Autowired
	private EntityManager entityManager;
	
	@Transactional
	public Cliente salvar(Cliente cliente) {
		entityManager.persist(cliente);
		return cliente;
	}
	
	@Transactional
	public Cliente atualizar(Cliente cliente) {
		entityManager.merge(cliente);
		return cliente;
	}
	
	@Transactional
	public void deletar(Integer id) {
		Cliente cliente = entityManager.find(Cliente.class, id);
		entityManager.remove(cliente);
	}
	
	@Transactional(readOnly = true)
	public List<Cliente> obterTodosPorNome(String nome) {
		String jpql = "select c from Cliente c where c.nome like :nome";
		TypedQuery<Cliente> query = entityManager.createQuery(jpql, Cliente.class);
		query.setParameter("nome", "%" + nome + "%");
		return query.getResultList();
	}
	
	@Transactional(readOnly = true)
	public List<Cliente> obterTodos() {
		return entityManager
				.createQuery("from Cliente", Cliente.class)
				.getResultList();
	}
	
}

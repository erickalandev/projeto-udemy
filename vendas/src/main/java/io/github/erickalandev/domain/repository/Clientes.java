package io.github.erickalandev.domain.repository;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.lang.Nullable;
import org.springframework.stereotype.Repository;

import io.github.erickalandev.domain.entity.Cliente;

@Repository
public class Clientes {
	
	private static String INSERT = "INSERT INTO cliente(nome) VALUES (?)"; 
	private static String SELECT_ALL = "SELECT * FROM cliente"; 
	private static String UPDATE = "update cliente set nome = ? where id = ? "; 
	private static String DELETE = "delete from cliente where id = ? ";
	
	@Autowired
	private JdbcTemplate jdbcTemplate;
	
	public Cliente salvar(Cliente cliente) {
		jdbcTemplate.update(INSERT, cliente.getNome() );
		return cliente;
	}
	
	public Cliente atualizar(Cliente cliente) {
		jdbcTemplate.update(UPDATE, cliente.getNome(), cliente.getId());
		return cliente;
	}
	
	public void deletar(Integer id) {
		jdbcTemplate.update(DELETE, id);
	}
	
	public List<Cliente> obterTodosPorNome(String nome) {
		return jdbcTemplate.query(SELECT_ALL
				.concat(" WHERE nome LIKE '%" + nome + "%'"), 
				obterClienteMapper());
	}
	
	public List<Cliente> obterTodos() {
		return jdbcTemplate.query(SELECT_ALL, obterClienteMapper());
	}
	
	private RowMapper<Cliente> obterClienteMapper() {
		return new RowMapper<Cliente>() {
			@Override
			public Cliente mapRow(ResultSet rs, int rowNum) throws SQLException {
				Integer id = rs.getInt("id");
				String nome = rs.getString("nome");
				return new Cliente(id, nome);
			}
		};
	}
	
}

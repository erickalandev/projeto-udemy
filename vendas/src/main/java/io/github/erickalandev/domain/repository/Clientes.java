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
	
	@Autowired
	private JdbcTemplate jdbcTemplate;
	
	public Cliente salvar(Cliente cliente) {
		jdbcTemplate.update(INSERT, new Object[] { cliente.getNome() } );
		return cliente;
	}
	
	public List<Cliente> obterTodos() {
		return jdbcTemplate.query(SELECT_ALL, new RowMapper<Cliente>() {
			@Override
			public Cliente mapRow(ResultSet rs, int i) throws SQLException {
				Integer id = rs.getInt("id");
				String nome = rs.getString("nome");
				return new Cliente(id, nome);
			}
		});
	}
	
}

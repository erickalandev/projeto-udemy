package io.github.erickalandev.domain.entity;

import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.validation.constraints.NotEmpty;

import org.hibernate.validator.constraints.br.CPF;

import com.fasterxml.jackson.annotation.JsonIgnore;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name="cliente")
public class Cliente { 
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id")
	private Integer id;
	
	@NotEmpty( message = "{campo.nome.obrigatorio}")
	@Column(name="nome", length = 100)
	private String nome;
	
	@JsonIgnore
	@OneToMany(mappedBy = "cliente", fetch= FetchType.LAZY)
	private List<Pedido> pedidos;
	
	@Column(name="cpf", length = 11)
	@NotEmpty( message = "{campo.cpf.obrigatorio}")
	@CPF( message = "{campo.cpf.invalido}" )
	private String cpf;
	
	public Cliente(Integer id, String nome) {
		super();
		this.id = id;
		this.nome = nome;
	}
	
}

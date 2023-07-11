package io.github.erickalandev.localizacao.domain.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "tb_cidade")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Cidade {
	
	@Id
	@Column(name = "id_cidade")
	private Long id;
	
	@Column(name = "nome", length = 50)
	private String nome;
	
	@Column(name = "qtd_habitantes")
	private Long habitantes;
	
}

package io.github.erickalandev.localizacao.domain.repository.specs;

import org.springframework.data.jpa.domain.Specification;

import io.github.erickalandev.localizacao.domain.entity.Cidade;

public abstract class CidadeSpecs {

	public static Specification<Cidade> nomeEqual(String nome) {
		return (root, query, criteriaBuilder) -> criteriaBuilder.equal(root.get("nome"), nome);
	}
	
	public static Specification<Cidade> habitanteSpecification(Long value) {
		return (root, query, criteriaBuilder) -> criteriaBuilder.greaterThan(root.get("habitantes"), value);
	}
	
	public static Specification<Cidade> propertyEqual(String propriedade, Object value) {
		return (root, query, criteriaBuilder) -> criteriaBuilder.equal(root.get(propriedade), value); 
	}
	
	public static Specification<Cidade> habitanteBetween(Long min, Long max) {
		return (root, query, criteriaBuilder) -> criteriaBuilder.between(root.get("habitantes"), min, max);
	}
	
	public static Specification<Cidade> nomeLike(String nome) {
		return (root, query, criteriaBuilder) -> criteriaBuilder.like(criteriaBuilder.upper(root.get("nome")), "%" + nome + "%".toUpperCase());
	}
	
}

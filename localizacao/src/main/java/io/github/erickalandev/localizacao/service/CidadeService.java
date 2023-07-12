package io.github.erickalandev.localizacao.service;

import java.util.List;

import org.springframework.data.domain.Example;
import org.springframework.data.domain.ExampleMatcher;
import org.springframework.data.domain.ExampleMatcher.StringMatcher;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import io.github.erickalandev.localizacao.domain.entity.Cidade;
import io.github.erickalandev.localizacao.domain.repository.CidadeRepository;
import io.github.erickalandev.localizacao.domain.repository.specs.CidadeSpecs;

@Service
public class CidadeService {

	private CidadeRepository cidadeRepository;

	public CidadeService(CidadeRepository cidadeRepository) {
		this.cidadeRepository = cidadeRepository;
	}

	public void findAll() {
		cidadeRepository.findAll().forEach(System.out::println);
	}

	public void listaNomes() {
		/*
		 * cidadeRepository.findByNome("Sao Paulo").forEach(System.out::println);
		 * cidadeRepository.findByNomeStartingWith("Sao").forEach(System.out::println);
		 * cidadeRepository.findByNomeEndingWith("Paulo").forEach(System.out::println);
		 * cidadeRepository.findByNomeContaining("Pa").forEach(System.out::println);
		 * cidadeRepository.findByNomeLike("%e%").forEach(System.out::println);
		 * cidadeRepository.findByNomeLike("%e%", Sort.by("habitantes")).forEach(System.out::println);
		 */
		
		Pageable pageable = PageRequest.of(0, 10);
		cidadeRepository.findByNomeLike("%or%", pageable).forEach(System.out::println);
	}
	
	public void listaNomesSqlNativoProjection() {
		cidadeRepository.findByNomeSqlNativoProjection("Sao Paulo")
						.stream().map( cidadeProjection -> new Cidade(cidadeProjection.getId(), cidadeProjection.getNome(), null))
						.forEach(System.out::println);
	}

	public void listarCidadesPorQuantidadeHabitantes() {
		cidadeRepository.findByHabitantesLessThan(1212323L).forEach(System.out::println);
		cidadeRepository.findByHabitantesLessThanEqual(1212323L).forEach(System.out::println);

		cidadeRepository.findByHabitantesGreaterThan(1212323L).forEach(System.out::println);
		cidadeRepository.findByHabitantesGreaterThanEqual(1212323L).forEach(System.out::println);
		cidadeRepository.findByHabitantesGreaterThanEqualAndNomeLike(1212323L, "Fortaleza").forEach(System.out::println);
	}

	@Transactional
	public void salvar() {
		var cidade = new Cidade(1L, "sao paulo", 12396372L);
		cidadeRepository.save(cidade);
	}
	
	public List<Cidade> filtroDinamico(Cidade cidade) {
		ExampleMatcher exampleMatcher = ExampleMatcher
				.matching()
				.withIgnoreCase("nome")
				.withStringMatcher(ExampleMatcher.StringMatcher.STARTING);
		Example<Cidade> example = Example.of(cidade, exampleMatcher);
		return cidadeRepository.findAll(example);
	}
	
	public void listaNomesSqlNative() {
		cidadeRepository.findByNomeLike("Sao Paulo").forEach(System.out::println);
	}
	
	public void listarCidadesByNomeSpecification() {
//		Specification<Cidade> spec = CidadeSpecs.nomeEqual("Sao Paulo");
//		Specification<Cidade> spec = CidadeSpecs.nomeEqual("Sao Paulo").and(CidadeSpecs.habitanteSpecification(1000));
		Specification<Cidade> spec = CidadeSpecs.propertyEqual("nome", "Sao Paulo");
		cidadeRepository.findAll(spec).forEach(System.out::println);
	}
	
	public void listarCidadesSpecsFiltroDinamico(Cidade filtro) {
		Specification<Cidade> specs = Specification.where((root, query, criteriaBuilder) -> criteriaBuilder.conjunction());
		
		if (StringUtils.hasText(filtro.getNome())) {
			specs = specs.and(CidadeSpecs.nomeLike(filtro.getNome()));
		}
		if (filtro.getHabitantes() != null) {
			specs = specs.and(CidadeSpecs.habitanteSpecification(filtro.getHabitantes()));
		}
		
		cidadeRepository.findAll(specs);
	}

}

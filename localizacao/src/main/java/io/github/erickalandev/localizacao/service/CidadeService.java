package io.github.erickalandev.localizacao.service;

import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import io.github.erickalandev.localizacao.domain.entity.Cidade;
import io.github.erickalandev.localizacao.domain.repository.CidadeRepository;

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
		// todos aqui funciona sem camel sensitive, a pesquisa tem que ser igual ao dado
		// do banco
		/*
		 * cidadeRepository.findByNome("Sao Paulo").forEach(System.out::println);
		 * cidadeRepository.findByNomeStartingWith("Sao").forEach(System.out::println);
		 * cidadeRepository.findByNomeEndingWith("Paulo").forEach(System.out::println);
		 * cidadeRepository.findByNomeContaining("Pa").forEach(System.out::println);
		// o like alem de conter essas pesquisas acima, ele funciona com camel sensitive
		 * cidadeRepository.findByNomeLike("%e%").forEach(System.out::println);
		//lista dos nomes ordenado por habitantes do menor para o maior
		 * cidadeRepository.findByNomeLike("%e%", Sort.by("habitantes")).forEach(System.out::println);
		 */
		
		
		//lista dos nomes paginados
		Pageable pageable = PageRequest.of(0, 3);
		cidadeRepository.findByNomeLike("%or%", pageable).forEach(System.out::println);
	}

	public void listarCidadesPorQuantidadeHabitantes() {
		// menor que
		cidadeRepository.findByHabitantesLessThan(1212323L).forEach(System.out::println);
		cidadeRepository.findByHabitantesLessThanEqual(1212323L).forEach(System.out::println);

		// maior que
		cidadeRepository.findByHabitantesGreaterThan(1212323L).forEach(System.out::println);
		cidadeRepository.findByHabitantesGreaterThanEqual(1212323L).forEach(System.out::println);
		cidadeRepository.findByHabitantesGreaterThanEqualAndNomeLike(1212323L, "Fortaleza").forEach(System.out::println);
	}

	@Transactional
	public void salvar() {
		var cidade = new Cidade(1L, "sao paulo", 12396372L);
		cidadeRepository.save(cidade);
	}

}

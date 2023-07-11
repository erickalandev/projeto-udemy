
package io.github.erickalandev.localizacao.domain.repository;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Order;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.transaction.annotation.Transactional;

import io.github.erickalandev.localizacao.domain.entity.Cidade;
import lombok.experimental.var;

public interface CidadeRepository extends JpaRepository<Cidade, Long> {
	
	List<Cidade> findByNome(String nome);
	
	//comeca com a palavra
	List<Cidade> findByNomeStartingWith(String nome);
	
	//termina com a palavra
	List<Cidade> findByNomeEndingWith(String nome);
	
	//contem a palavra em qualquer lugar
	List<Cidade> findByNomeContaining(String nome);
	
	//metodo LIKE contem todos esses metodos de busca por nome listado acima
	//o like por padrao ja vem com case sensitive, mas podemos colocar uma comparacao deixando todos com letras maiuscula tbm
	@Query("select c from Cidade c where upper(c.nome) like upper(?1)")
	List<Cidade> findByNomeLike(String nome);
	
	//busca pelo nome ordenado
	@Query("select c from Cidade c where upper(c.nome) like upper(?1)")
	List<Cidade> findByNomeLike(String nome, Sort sort);
	
	//busca pelo nome paginado
	@Query("select c from Cidade c where upper(c.nome) like upper(?1)")
	Page<Cidade> findByNomeLike(String nome, Pageable pageable);
	
	List<Cidade> findByHabitantes(Long habitantes);
	
	//cidade que tem menos que o valor da busca(habitantes)
	List<Cidade> findByHabitantesLessThan(Long habitantes);
	
	//cidade que tem menos ou igual que o valor da busca(habitantes)
	List<Cidade> findByHabitantesLessThanEqual(Long habitantes);
	
	//cidade que tem mais que o valor da busca(habitantes)
	List<Cidade> findByHabitantesGreaterThan(Long habitantes);
	
	//cidade que tem mais ou igual que o valor da busca(habitantes)
	List<Cidade> findByHabitantesGreaterThanEqual(Long habitantes);
	
	//cidade que tem mais ou igual que o valor da busca(habitantes) e nome
	List<Cidade> findByHabitantesGreaterThanEqualAndNomeLike(Long habitantes, String nome);
}

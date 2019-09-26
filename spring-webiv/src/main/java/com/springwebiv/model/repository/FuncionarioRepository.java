package com.springwebiv.model.repository;

import java.time.LocalDate;
import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.springwebiv.model.entity.Funcionario;

public interface FuncionarioRepository extends JpaRepository<Funcionario, Long> {

	@Override
	@EntityGraph(attributePaths = "departamento")
	public Optional<Funcionario> findById(Long id);
	
	
	public Page<Funcionario> findByDepartamentoId(Long id, Pageable pageable);
	
	@Query("FROM Funcionario funcionario "
			+ "WHERE "
			+ "(funcionario.nome LIKE '%' || :nome || '%' OR :nome IS NULL) "
			+ " AND (funcionario.cpf LIKE '%' || :cpf || '%' OR :cpf IS NULL) "
			+ "AND (( funcionario.dataNascimento >= :dataNascimentoInicial OR CAST(:dataNascimentoInicial AS timestamp) IS NULL) "
			+ "AND ( funcionario.dataNascimento <= :dataNascimentoFinal OR CAST(:dataNascimentoFinal AS timestamp) IS NULL))")
	public Page<Funcionario> findByFilters(@Param("nome") String nome, @Param("cpf") String cpf, 
			@Param("dataNascimentoInicial") LocalDate dataNascimentoInicial, @Param("dataNascimentoFinal") LocalDate dataNascimentoFinal, Pageable pageable);
	
	
	@Query("FROM Funcionario funcionario "
			+ "WHERE ( funcionario.nome LIKE '%' || :nome || '%' OR :nome IS NULL) AND "
			+ "( funcionario.cpf LIKE '%' || :cpf || '%' OR :cpf IS NULL)")
	public Page<Funcionario> findByFilters(@Param("nome") String nome, @Param("cpf") String cpf, Pageable pageable);
}

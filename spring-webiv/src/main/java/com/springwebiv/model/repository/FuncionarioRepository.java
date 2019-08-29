package com.springwebiv.model.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.springwebiv.model.entity.Funcionario;

public interface FuncionarioRepository extends JpaRepository<Funcionario, Long> {

}

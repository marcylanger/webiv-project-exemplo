package com.springwebiv.model.service;

import java.util.List;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

import com.springwebiv.model.entity.Funcionario;
import com.springwebiv.model.repository.FuncionarioRepository;


@Service
@Transactional
public class FuncionarioService {

	@Autowired
	private FuncionarioRepository funcionarioRepository;
	
	public Funcionario cadastrarFuncionario(Funcionario funcionario) {
		
		Assert.isTrue(funcionario.getIdade() >= 16, "O funcionario deve ser maior de 16 anos");

		return this.funcionarioRepository.save(funcionario);
	}
	
	
	public List<Funcionario> listarFuncionarios(){
		return this.funcionarioRepository.findAll();
	}
	
	public Funcionario detalharFuncionario(Long id) {
		return this.funcionarioRepository.findById(id).orElse(null);
	}
}

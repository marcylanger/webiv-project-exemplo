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
	
	
	/**
	 * Serviço para inserir um novo funcionário
	 * 
	 * @param funcionario
	 * @return
	 */
	public Funcionario cadastrarFuncionario(Funcionario funcionario) {
		return this.funcionarioRepository.save(funcionario);
	}
	
	/**
	 * Serviço para atualizar o cadastro de um funcionario
	 * @param funcionario
	 * @return
	 */
	public Funcionario atualizarFuncionario(Funcionario funcionario) {
		return this.funcionarioRepository.save(funcionario);
	}
	
	/**
	 * Serviço para listar os funcionários cadastrados
	 * @return
	 */
	public List<Funcionario> listarFuncionarios(){
		return this.funcionarioRepository.findAll();
	}
	

	/**
	 * Serviço para detalhar o cadastro de um funcionário
	 * @param id
	 * @return
	 */
	public Funcionario detalharFuncionario(long id) {
		
		Funcionario  funcionario = this.funcionarioRepository.findById(id).orElse(null);
		
		Assert.notNull(funcionario, "O Id "+ id +" não foi encontrado.");
		
		return funcionario;
	}
	
	/**
	 * Serviço que remove um funcionário cadastrado
	 * @param id
	 */
	public void removerFuncionario(long id) {
		this.funcionarioRepository.deleteById(id);
	}
	
	
	
	
	
	

	
	
	
	
}

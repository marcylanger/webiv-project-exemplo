package com.springwebiv.model.service;

import java.util.List;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

import com.springwebiv.model.entity.Departamento;
import com.springwebiv.model.repository.DepartamentoRepository;

@Service
@Transactional
public class DepartamentoService {

	@Autowired
	private DepartamentoRepository departamentoRepository;
	
	/**
	 * Serviço para cadastrar um novo departamento
	 * @param departamento
	 * @return
	 */
	public Departamento cadastrarDepartamento(Departamento departamento) {
		return this.departamentoRepository.save(departamento);
	}
	
	public Departamento atualizarDepartamento(Departamento departamento) {
		return this.departamentoRepository.save(departamento);
	}
	
	public void removerDepartamento(long id) {
		this.departamentoRepository.deleteById(id);
	}
	
	public List<Departamento> listarDepartamentos(){
		return this.departamentoRepository.findAll();
	}
	
	public Departamento detalharDepartamento(long id) {
		Departamento dep = this.departamentoRepository.findById(id).orElse(null);
		Assert.notNull(dep, "O Id "+ id +" não foi encontrado.");
		return dep;
	}
}

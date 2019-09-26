package com.springwebiv.model.service;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

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
}

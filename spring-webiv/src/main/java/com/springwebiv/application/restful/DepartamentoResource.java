package com.springwebiv.application.restful;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.springwebiv.model.entity.Departamento;
import com.springwebiv.model.entity.Funcionario;
import com.springwebiv.model.service.DepartamentoService;

@Component
@RestController
@RequestMapping( "/api/departamento" )
public class DepartamentoResource {

	@Autowired
	private DepartamentoService departamentoService;
	
	@GetMapping("/list")
	public List<Departamento> listar() {
		return this.departamentoService.listarDepartamentos();
	}
	
	@PostMapping( "/insert" )
	public Departamento cadastrar( @RequestBody Departamento departamento )
	{
		return this.departamentoService.cadastrarDepartamento(departamento);
	}
	
	@PostMapping( "/update" )
	public Departamento atualizar( @RequestBody Departamento departamento )
	{
		return this.departamentoService.atualizarDepartamento(departamento);
	}

	@GetMapping("/find")
	public Departamento detalhar(@RequestParam("id") Long id) {
		return this.departamentoService.detalharDepartamento(id);
	}
	
	@GetMapping("/remove")
	public void remover(@RequestParam("id") Long id) {
		this.departamentoService.removerDepartamento(id);;
	}
}

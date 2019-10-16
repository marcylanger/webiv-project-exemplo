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

import com.springwebiv.model.entity.Funcionario;
import com.springwebiv.model.service.FuncionarioService;


@Component
@RestController
@RequestMapping( "/api/funcionario" )
public class FuncionarioResource {
	
	
	@Autowired
	private FuncionarioService funcionarioService;
	
	@GetMapping("/list")
	public List<Funcionario> listar() {
		return this.funcionarioService.listarFuncionarios();
	}
	
	@GetMapping("/find")
	public Funcionario detalhar(@RequestParam("id") Long id) {
		return this.funcionarioService.detalharFuncionario(id);
	}
	
	
	@GetMapping("/remove")
	public void remover(@RequestParam("id") Long id) {
		this.funcionarioService.removerFuncionario(id);
	}
	
	@PostMapping( "/insert" )
	public Funcionario cadastrar( @RequestBody Funcionario funcionario )
	{
		return this.funcionarioService.cadastrarFuncionario(funcionario);
	}
	

	@PostMapping( "/update" )
	public Funcionario atualizar( @RequestBody Funcionario funcionario )
	{
		return this.funcionarioService.atualizarFuncionario(funcionario);
	}
}




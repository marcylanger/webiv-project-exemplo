package com.springwebiv.model.service;

import java.util.List;

import org.junit.Assert;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.jdbc.Sql;

import com.springwebiv.model.entity.Funcionario; 


public class FuncionarioTests extends AbstractIntegrationTests
{

	@Autowired
	private FuncionarioService funcionarioService;
	
	@Test
	@Sql({
		"/dataset/truncate.sql",
		"/dataset/funcionarios.sql"
	})
	public void listarFuncionariosMustPass()
	{
		List<Funcionario> funcionarios = this.funcionarioService.listarFuncionarios();
		Assert.assertEquals( funcionarios.size(), 1 );
		
	}
}

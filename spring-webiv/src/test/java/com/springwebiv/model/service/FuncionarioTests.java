package com.springwebiv.model.service;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.Month;
import java.util.List;

import org.junit.Assert;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.test.context.jdbc.Sql;

import com.springwebiv.model.entity.CargoEnum;
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
	
	@Test
	@Sql({
		"/dataset/truncate.sql",
		"/dataset/funcionarios.sql"
	})
	public void cadastrarFuncionarioMustPass() {
		Funcionario funcionario = new 
				Funcionario();
		funcionario.setNome("Maria");
		funcionario.setSalario(new BigDecimal(30000));
		funcionario.setCpf("44444444444");
		funcionario.setDataNascimento(LocalDate.of(1990, Month.JANUARY, 1));
		funcionario.setCargo(CargoEnum.ANALISTA_SISTEMAS);
	
		funcionarioService.cadastrarFuncionario(funcionario);
		
		Assert.assertNotNull(funcionario.getId());
		
	}
	
	@Test
	@Sql({
		"/dataset/truncate.sql",
		"/dataset/funcionarios.sql"
	})
	public void cadastrarFuncionarioMustPassVerificandoIdade() {
		Funcionario funcionario = new 
				Funcionario();
		funcionario.setNome("Maria");
		funcionario.setSalario(new BigDecimal(30000));
		funcionario.setCpf("44444444444");
		funcionario.setDataNascimento(LocalDate.of(1990, Month.JANUARY, 1));
		funcionario.setCargo(CargoEnum.ANALISTA_SISTEMAS);
	
		funcionarioService.cadastrarFuncionario(funcionario);
		
		Assert.assertNotNull(funcionario.getId());
		Assert.assertTrue(funcionario.getIdade().equals(29));
		
	}
	
	@Test(expected = DataIntegrityViolationException.class)
	@Sql({
		"/dataset/truncate.sql",
		"/dataset/funcionarios.sql"
	})
	 public void cadastrarFuncionarioMustFailCpfDuplicado() {
		 Funcionario funcionario = new 
					Funcionario();
			funcionario.setNome("Maria");
			funcionario.setSalario(new BigDecimal(30000));
			funcionario.setCpf("22222222222");
			funcionario.setDataNascimento(LocalDate.of(1995, Month.JANUARY, 1));
			funcionario.setCargo(CargoEnum.ANALISTA_SISTEMAS);
			
			funcionarioService.cadastrarFuncionario(funcionario);
			
			
	 }
	
	@Test(expected = IllegalArgumentException.class)
	@Sql({
		"/dataset/truncate.sql",
		"/dataset/funcionarios.sql"
	})
	public void cadastrarFuncionarioMustFailIdadeMenor() {
		Funcionario funcionario = new 
				Funcionario();
		funcionario.setNome("Maria");
		funcionario.setSalario(new BigDecimal(30000));
		funcionario.setCpf("64444444444");
		funcionario.setDataNascimento(LocalDate.of(2010, Month.JANUARY, 1));
		funcionario.setCargo(CargoEnum.ANALISTA_SISTEMAS);
	
		funcionarioService.cadastrarFuncionario(funcionario);
	
		
	}
	
	
	@Test
	@Sql({
		"/dataset/truncate.sql",
		"/dataset/funcionarios.sql"
	})
	public void detalharFuncionarioMustPass() {
		Funcionario funcionario = this.funcionarioService.detalharFuncionario(1001L);
		
		Assert.assertNotNull(funcionario);
		Assert.assertNotNull(funcionario.getId());
		Assert.assertEquals(funcionario.getCpf(), "22222222222");
	}
	
	
	
	
	
	
	
	
	
}

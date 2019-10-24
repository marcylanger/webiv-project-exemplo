package com.springwebiv.model.service;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.Month;

import javax.validation.ValidationException;

import org.junit.Assert;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.transaction.TransactionSystemException;

import com.springwebiv.model.entity.CargoEnum;
import com.springwebiv.model.entity.Departamento;
import com.springwebiv.model.entity.Funcionario;
import com.springwebiv.model.repository.DepartamentoRepository;
import com.springwebiv.model.repository.FuncionarioRepository;

public class DepartamentoTests extends AbstractIntegrationTests {

	@Autowired
	private DepartamentoService departamentoService;
	
	@Autowired
	private DepartamentoRepository departamentoRepository;
	
	@Autowired
	private FuncionarioRepository funcionarioRepository;
	
	/**
	 * ====================================== CADASTRAR ===========================================
	 */
	@Test
	@Sql({ "/dataset/truncate.sql",  
		"/dataset/departamentos.sql"})
	public void cadastrarDepartamentoMustPass() {
		
		Departamento departamento = new Departamento();
		departamento.setNome("Desenvolvimento");
		departamento.setDescricao("Departamento de desenvolvimento de sistemas");
		
		departamentoService.cadastrarDepartamento(departamento);
		
		Assert.assertNotNull(departamento.getId());
		
	}
	
	@Test
	@Sql({ "/dataset/truncate.sql",  
		"/dataset/departamentos.sql"})
	public void cadastrarDepartamentoMustPassInserirFuncionarios() {
		
		Departamento departamento = new Departamento();
		departamento.setNome("Desenvolvimento");
		departamento.setDescricao("Departamento de desenvolvimento de sistemas");
		
		Funcionario func1 = new Funcionario();
		func1.setNome("Maria Teresa");
		func1.setCargo(CargoEnum.ANALISTA_SISTEMAS);
		func1.setCpf("77777777777");
		func1.setDataNascimento(LocalDate.of(1990, Month.JANUARY, 1));
		func1.setSalario(new BigDecimal(34500));
		func1.setDepartamento(departamento);
		
		departamento.getFuncionarios().add(func1);
		
		Funcionario func2 = new Funcionario();
		func2.setNome("Maria Antonia");
		func2.setCargo(CargoEnum.DESENVOLVEDOR);
		func2.setCpf("88888888888");
		func2.setDataNascimento(LocalDate.of(1990, Month.JANUARY, 1));
		func2.setSalario(new BigDecimal(34500));
		func2.setDepartamento(departamento);
		
		departamento.getFuncionarios().add(func2);
		
		departamentoService.cadastrarDepartamento(departamento);
		
		Assert.assertNotNull(departamento.getId());
		Assert.assertTrue(departamento.getFuncionarios().size() == 2);
		
		for (Funcionario func : departamento.getFuncionarios()) {
			Assert.assertNotNull(func.getId());
			
		}
		
		
	}
	
	@Test(expected = ValidationException.class)
	@Sql({ "/dataset/truncate.sql",  
		"/dataset/departamentos.sql"})
	public void cadastrarDepartamentoMustFailSemNome() {
		
		Departamento departamento = new Departamento();
		departamento.setNome("");
		departamento.setDescricao("Departamento de desenvolvimento de sistemas");
		
		departamentoService.cadastrarDepartamento(departamento);
		
		
	}
	
	/**
	 * ====================================== ATUALIZAR ===========================================
	 */
	
	@Test
	@Sql({ "/dataset/truncate.sql",  
		"/dataset/departamentos.sql", 
		"/dataset/funcionarios.sql" })
	public void atualizarDepartamentoMustPassMantendoFuncionarios() {
		
		Departamento departamento = this.departamentoRepository.findById(1001L).orElse(null);
		Assert.assertTrue(departamento.getFuncionarios().size() == 2);
		
		departamento.getFuncionarios().remove(1);
		Funcionario func1 = new Funcionario();
		func1.setNome("Maria Teresa");
		func1.setCargo(CargoEnum.ANALISTA_SISTEMAS);
		func1.setCpf("77777777777");
		func1.setDataNascimento(LocalDate.of(1990, Month.JANUARY, 1));
		func1.setSalario(new BigDecimal(34500));
		func1.setDepartamento(departamento);
		
		departamento.getFuncionarios().add(func1);
		
		Funcionario func2 = new Funcionario();
		func2.setNome("Maria Antonia");
		func2.setCargo(CargoEnum.DESENVOLVEDOR);
		func2.setCpf("88888888888");
		func2.setDataNascimento(LocalDate.of(1990, Month.JANUARY, 1));
		func2.setSalario(new BigDecimal(34500));
		func2.setDepartamento(departamento);
		
		departamento.getFuncionarios().add(func2);
		
		departamentoService.atualizarDepartamento(departamento);
		
		Assert.assertNotNull(departamento.getId());
		Assert.assertTrue(departamento.getFuncionarios().size() == 3);
		
	}
	
	@Test
	@Sql({ "/dataset/truncate.sql",  
		"/dataset/departamentos.sql", 
		"/dataset/funcionarios.sql" })
	public void atualizarDepartamentoMust() {
		
		Departamento departamento = this.departamentoRepository.findById(1001L).orElse(null);
		departamento.setNome("Desenvolvimento Atualizado");
		departamento.setDescricao("Departamento de desenvolvimento de sistemas");
		
		this.departamentoService.atualizarDepartamento(departamento);
		
		Assert.assertNotNull(departamento.getId());
		Assert.assertEquals("Desenvolvimento Atualizado", departamento.getNome());;
		
		
		
	}
	
	@Test(expected = TransactionSystemException.class)
	@Sql({ "/dataset/truncate.sql",  
		"/dataset/departamentos.sql", 
		"/dataset/funcionarios.sql" })
	public void atualizarDepartamentoMustFailSemNome() {
		
		Departamento departamento = this.departamentoRepository.findById(1001L).orElse(null);
		departamento.setNome(null);
		
		this.departamentoService.atualizarDepartamento(departamento);
		
		
	}
	
	
	/**
	 * ====================================== Remover ===========================================
	 */
	
	@Test
	@Sql({ "/dataset/truncate.sql",  
		"/dataset/departamentos.sql", 
		"/dataset/funcionarios.sql" })
	public void removerDepartamentoMustPassComFuncionarios() {
		this.departamentoService.removerDepartamento(1001L);
		
		Departamento departamento = this.departamentoRepository.findById(1001L).orElse(null);
		Assert.assertNull(departamento);
		
		
		Funcionario funcionario1 = 
				this.funcionarioRepository.findById(1001L).orElse(null);
		
		Assert.assertNull(funcionario1);
		
		Funcionario funcionario2 = 
				this.funcionarioRepository.findById(1002L).orElse(null);
		
		Assert.assertNull(funcionario2);
		
	}
	
	
	
}

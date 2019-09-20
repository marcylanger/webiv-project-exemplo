package com.springwebiv.model.service;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.Month;
import java.util.List;

import org.junit.Assert;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.dao.InvalidDataAccessApiUsageException;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.transaction.TransactionSystemException;

import com.springwebiv.model.entity.CargoEnum;
import com.springwebiv.model.entity.Funcionario;
import com.springwebiv.model.repository.FuncionarioRepository;

public class FuncionarioTests extends AbstractIntegrationTests {

	@Autowired
	private FuncionarioService funcionarioService;

	@Autowired
	private FuncionarioRepository funcionarioRepository;

	/**
	 * ====================================== LISTAR ===========================================
	 */
	@Test
	@Sql({ "/dataset/truncate.sql", "/dataset/funcionarios.sql" })
	public void listarFuncionariosMustPass() {
		List<Funcionario> funcionarios = this.funcionarioService.listarFuncionarios();
		Assert.assertEquals(funcionarios.size(), 2);

	}

	/**
	 * ====================================== CADASTRAR ===========================================
	 */
	@Test
	@Sql({ "/dataset/truncate.sql", "/dataset/funcionarios.sql" })
	public void cadastrarFuncionarioMustPass() {
		Funcionario funcionario = new Funcionario();
		funcionario.setNome("Maria");
		funcionario.setSalario(new BigDecimal(30000));
		funcionario.setCpf("44444444444");
		funcionario.setDataNascimento(LocalDate.of(1990, Month.JANUARY, 1));
		funcionario.setCargo(CargoEnum.GERENTE_PROJETOS);

		funcionarioService.cadastrarFuncionario(funcionario);

		Assert.assertNotNull(funcionario.getId());

	}

	@Test
	@Sql({ "/dataset/truncate.sql", "/dataset/funcionarios.sql" })
	public void cadastrarFuncionarioMustPassVerificandoIdade() {
		Funcionario funcionario = new Funcionario();
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
	@Sql({ "/dataset/truncate.sql", "/dataset/funcionarios.sql" })
	public void cadastrarFuncionarioMustFailCpfDuplicado() {
		Funcionario funcionario = new Funcionario();
		funcionario.setNome("Maria");
		funcionario.setSalario(new BigDecimal(30000));
		funcionario.setCpf("22222222222");
		funcionario.setDataNascimento(LocalDate.of(1995, Month.JANUARY, 1));
		funcionario.setCargo(CargoEnum.ANALISTA_SISTEMAS);

		funcionarioService.cadastrarFuncionario(funcionario);

	}

	@Test(expected = InvalidDataAccessApiUsageException.class)
	@Sql({ "/dataset/truncate.sql", "/dataset/funcionarios.sql" })
	public void cadastrarFuncionarioMustFailIdadeMenor() {
		Funcionario funcionario = new Funcionario();
		funcionario.setNome("Maria");
		funcionario.setSalario(new BigDecimal(30000));
		funcionario.setCpf("64444444444");
		funcionario.setDataNascimento(LocalDate.of(2010, Month.JANUARY, 1));
		funcionario.setCargo(CargoEnum.ANALISTA_SISTEMAS);

		funcionarioService.cadastrarFuncionario(funcionario);

	}

	@Test(expected = TransactionSystemException.class)
	@Sql({ "/dataset/truncate.sql", "/dataset/funcionarios.sql" })
	public void cadastrarFuncionarioMustFailNomeEmBranco() {
		Funcionario funcionario = new Funcionario();
		funcionario.setNome("");
		funcionario.setSalario(new BigDecimal(30000));
		funcionario.setCpf("64444444444");
		funcionario.setDataNascimento(LocalDate.of(2000, Month.JANUARY, 1));
		funcionario.setCargo(CargoEnum.ANALISTA_SISTEMAS);

		this.funcionarioService.cadastrarFuncionario(funcionario);
	}

	/**
	 * 
	 * ======================== ATUALIZAR ============================
	 */

	@Test
	@Sql({ "/dataset/truncate.sql", "/dataset/funcionarios.sql" })
	public void atualizarFuncionarioMustPass() {
		Funcionario funcionario = this.funcionarioRepository.findById(1001L).orElse(null);
		funcionario.setDataNascimento(LocalDate.of(1990, Month.JANUARY, 1));

		funcionarioService.atualizarFuncionario(funcionario);

		Assert.assertTrue(funcionario.getDataNascimento().getYear() == 1990);

	}

	@Test
	@Sql({ "/dataset/truncate.sql", "/dataset/funcionarios.sql" })
	public void atualizarFuncionarioMustPassVerificandoIdade() {
		Funcionario funcionario = this.funcionarioRepository.findById(1001L).orElse(null);
		funcionario.setDataNascimento(LocalDate.of(1990, Month.JANUARY, 1));

		funcionarioService.atualizarFuncionario(funcionario);

		Assert.assertTrue(funcionario.getIdade().equals(29));

	}

	@Test(expected = DataIntegrityViolationException.class)
	@Sql({ "/dataset/truncate.sql", "/dataset/funcionarios.sql" })
	public void atualizarFuncionarioMustFailCpfDuplicado() {
		Funcionario funcionario = this.funcionarioRepository.findById(1001L).orElse(null);

		funcionario.setDataNascimento(LocalDate.of(1990, Month.JANUARY, 1));
		funcionario.setCpf("33333333333");
	
		funcionarioService.atualizarFuncionario(funcionario);

	}

	@Test(expected = InvalidDataAccessApiUsageException.class)
	@Sql({ "/dataset/truncate.sql", "/dataset/funcionarios.sql" })
	public void atualizarFuncionarioMustFailIdadeMenor() {
		Funcionario funcionario = this.funcionarioRepository.findById(1001L).orElse(null);

		funcionario.setDataNascimento(LocalDate.of(2010, Month.JANUARY, 1));

		funcionarioService.atualizarFuncionario(funcionario);

	}

	@Test(expected = TransactionSystemException.class)
	@Sql({ "/dataset/truncate.sql", "/dataset/funcionarios.sql" })
	public void atualizarFuncionarioMustFailNomeEmBranco() {
		Funcionario funcionario = new Funcionario();
		funcionario.setNome("");
		funcionario.setSalario(new BigDecimal(30000));
		funcionario.setCpf("64444444444");
		funcionario.setDataNascimento(LocalDate.of(2000, Month.JANUARY, 1));
		funcionario.setCargo(CargoEnum.ANALISTA_SISTEMAS);

		this.funcionarioService.cadastrarFuncionario(funcionario);
	}

	/**
	 * ================== DETALHAR ===============================
	 */
	@Test()
	@Sql({ "/dataset/truncate.sql", "/dataset/funcionarios.sql" })
	public void detalharFuncionarioMustPass() {
		Funcionario funcionario = this.funcionarioService.detalharFuncionario(1001L);

		Assert.assertNotNull(funcionario);
		Assert.assertNotNull(funcionario.getId());
		Assert.assertEquals(funcionario.getCpf(), "22222222222");

	}

}

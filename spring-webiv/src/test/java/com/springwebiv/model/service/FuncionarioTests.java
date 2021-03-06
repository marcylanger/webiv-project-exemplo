package com.springwebiv.model.service;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.Month;
import java.util.List;

import javax.validation.ValidationException;

import org.junit.Assert;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.dao.InvalidDataAccessApiUsageException;
import org.springframework.test.context.jdbc.Sql;

import com.springwebiv.model.entity.CargoEnum;
import com.springwebiv.model.entity.Departamento;
import com.springwebiv.model.entity.Funcionario;
import com.springwebiv.model.repository.DepartamentoRepository;
import com.springwebiv.model.repository.FuncionarioRepository;

public class FuncionarioTests extends AbstractIntegrationTests {

	@Autowired
	private FuncionarioService funcionarioService;

	@Autowired
	private FuncionarioRepository funcionarioRepository;

	@Autowired
	private DepartamentoRepository departamentoRepository;

	/**
	 * ====================================== LISTAR
	 * ===========================================
	 */
	@Test
	@Sql({ "/dataset/truncate.sql", "/dataset/departamentos.sql", "/dataset/funcionarios.sql",
			"/dataset/usuarios.sql" })
	public void listarFuncionariosMustPass() {
		List<Funcionario> funcionarios = this.funcionarioService.listarFuncionarios();
		Assert.assertEquals(funcionarios.size(), 4);

	}

	@Test
	@Sql({ "/dataset/truncate.sql", "/dataset/departamentos.sql", "/dataset/funcionarios.sql" })
	public void listarFuncionariosPorDepartamentoMustPass() {
		List<Funcionario> funcionarios = this.funcionarioService.listarFuncionariosPorDepartamento(1002L, null)
				.getContent();
		Assert.assertEquals(funcionarios.size(), 2);
	}

	@Test
	@Sql({ "/dataset/truncate.sql", "/dataset/departamentos.sql", "/dataset/funcionarios.sql" })
	public void listarFuncionariosPorFiltrosMustPassSemFiltros() {
		List<Funcionario> funcionarios = this.funcionarioService
				.listarFuncionariosPorFiltros(null, null, null, null, null).getContent();
		Assert.assertEquals(funcionarios.size(), 4);
	}

	@Test
	@Sql({ "/dataset/truncate.sql", "/dataset/departamentos.sql", "/dataset/funcionarios.sql" })
	public void listarFuncionariosPorFiltrosMustPassFiltrarPorNome() {
		List<Funcionario> funcionarios = this.funcionarioService
				.listarFuncionariosPorFiltros("mar", null, null, null, null).getContent();
		Assert.assertEquals(2, funcionarios.size());
	}

	@Test
	@Sql({ "/dataset/truncate.sql", "/dataset/departamentos.sql", "/dataset/funcionarios.sql" })
	public void listarFuncionariosPorFiltrosMustPassFiltrarPorCpf() {
		List<Funcionario> funcionarios = this.funcionarioService
				.listarFuncionariosPorFiltros(null, "222", null, null, null).getContent();
		Assert.assertEquals(1, funcionarios.size());
	}

	@Test
	@Sql({ "/dataset/truncate.sql", "/dataset/departamentos.sql", "/dataset/funcionarios.sql" })
	public void listarFuncionariosPorFiltrosMustPassFiltrarPorNomeCpf() {
		List<Funcionario> funcionarios = this.funcionarioService
				.listarFuncionariosPorFiltros("mar", "222", null, null, null).getContent();
		Assert.assertEquals(1, funcionarios.size());
	}

	/**
	 * ====================================== CADASTRAR
	 * ===========================================
	 */
	@Test
	@Sql({ "/dataset/truncate.sql", "/dataset/departamentos.sql", "/dataset/funcionarios.sql" })
	public void cadastrarFuncionarioMustPass() {
		Funcionario funcionario = new Funcionario();
		funcionario.setNome("Maria");
		funcionario.setSalario(new BigDecimal(30000));
		funcionario.setCpf("44444444444");
		funcionario.setDataNascimento(LocalDate.of(1990, Month.JANUARY, 1));
		funcionario.setCargo(CargoEnum.GERENTE_PROJETOS);
		Departamento departamento = this.departamentoRepository.findById(1001L).orElse(null);
		funcionario.setDepartamento(departamento);

		funcionarioService.cadastrarFuncionario(funcionario);

		Assert.assertNotNull(funcionario.getId());

	}

	@Test
	@Sql({ "/dataset/truncate.sql", "/dataset/departamentos.sql", "/dataset/funcionarios.sql" })
	public void cadastrarFuncionarioMustPassVerificandoIdade() {
		Funcionario funcionario = new Funcionario();
		funcionario.setNome("Maria");
		funcionario.setSalario(new BigDecimal(30000));
		funcionario.setCpf("44444444444");
		funcionario.setDataNascimento(LocalDate.of(1990, Month.JANUARY, 1));
		funcionario.setCargo(CargoEnum.ANALISTA_SISTEMAS);

		Departamento departamento = this.departamentoRepository.findById(1001L).orElse(null);
		funcionario.setDepartamento(departamento);

		funcionarioService.cadastrarFuncionario(funcionario);

		Assert.assertNotNull(funcionario.getId());
		Assert.assertTrue(funcionario.getIdade().equals(29));

	}

	@Test(expected = DataIntegrityViolationException.class)
	@Sql({ "/dataset/truncate.sql", "/dataset/departamentos.sql", "/dataset/funcionarios.sql" })
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
	@Sql({ "/dataset/truncate.sql", "/dataset/departamentos.sql", "/dataset/funcionarios.sql" })
	public void cadastrarFuncionarioMustFailIdadeMenor() {
		Funcionario funcionario = new Funcionario();
		funcionario.setNome("Maria");
		funcionario.setSalario(new BigDecimal(30000));
		funcionario.setCpf("64444444444");
		funcionario.setDataNascimento(LocalDate.of(2010, Month.JANUARY, 1));
		funcionario.setCargo(CargoEnum.ANALISTA_SISTEMAS);

		funcionarioService.cadastrarFuncionario(funcionario);

	}

	@Test(expected = ValidationException.class)
	@Sql({ "/dataset/truncate.sql", "/dataset/departamentos.sql", "/dataset/funcionarios.sql" })
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
	@Sql({ "/dataset/truncate.sql", "/dataset/departamentos.sql", "/dataset/funcionarios.sql" })
	public void atualizarFuncionarioMustPass() {
		Funcionario funcionario = this.funcionarioRepository.findById(1001L).orElse(null);
		funcionario.setDataNascimento(LocalDate.of(1990, Month.JANUARY, 1));

		funcionarioService.atualizarFuncionario(funcionario);

		Assert.assertTrue(funcionario.getDataNascimento().getYear() == 1990);

	}

	@Test
	@Sql({ "/dataset/truncate.sql", "/dataset/departamentos.sql", "/dataset/funcionarios.sql" })
	public void atualizarFuncionarioMustPassVerificandoIdade() {
		Funcionario funcionario = this.funcionarioRepository.findById(1001L).orElse(null);
		funcionario.setDataNascimento(LocalDate.of(1990, Month.JANUARY, 1));

		funcionarioService.atualizarFuncionario(funcionario);

		Assert.assertTrue(funcionario.getIdade().equals(29));

	}

	@Test(expected = DataIntegrityViolationException.class)
	@Sql({ "/dataset/truncate.sql", "/dataset/departamentos.sql", "/dataset/funcionarios.sql" })
	public void atualizarFuncionarioMustFailCpfDuplicado() {
		Funcionario funcionario = this.funcionarioRepository.findById(1001L).orElse(null);

		funcionario.setDataNascimento(LocalDate.of(1990, Month.JANUARY, 1));
		funcionario.setCpf("33333333333");

		funcionarioService.atualizarFuncionario(funcionario);

	}

	@Test(expected = InvalidDataAccessApiUsageException.class)
	@Sql({ "/dataset/truncate.sql", "/dataset/departamentos.sql", "/dataset/funcionarios.sql" })
	public void atualizarFuncionarioMustFailIdadeMenor() {
		Funcionario funcionario = this.funcionarioRepository.findById(1001L).orElse(null);

		funcionario.setDataNascimento(LocalDate.of(2010, Month.JANUARY, 1));

		funcionarioService.atualizarFuncionario(funcionario);

	}

	@Test(expected = ValidationException.class)
	@Sql({ "/dataset/truncate.sql", "/dataset/departamentos.sql", "/dataset/funcionarios.sql" })
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
	@Sql({ "/dataset/truncate.sql", "/dataset/departamentos.sql", "/dataset/funcionarios.sql" })
	public void detalharFuncionarioMustPass() {
		Funcionario funcionario = this.funcionarioService.detalharFuncionario(1001L);

		Assert.assertNotNull(funcionario);
		Assert.assertNotNull(funcionario.getId());
		Assert.assertEquals(funcionario.getCpf(), "22222222222");

	}

	@Test()
	@Sql({ "/dataset/truncate.sql", "/dataset/departamentos.sql", "/dataset/funcionarios.sql" })
	public void detalharFuncionarioMustPassVerificaDepartamento() {
		Funcionario funcionario = this.funcionarioService.detalharFuncionario(1001L);

		Assert.assertNotNull(funcionario);
		Assert.assertNotNull(funcionario.getDepartamento());
		Assert.assertNotNull(funcionario.getDepartamento().getId());
		Assert.assertEquals(funcionario.getDepartamento().getNome(), "Desenvolvimento");

	}

	@Test(expected = IllegalArgumentException.class)
	@Sql({ "/dataset/truncate.sql", "/dataset/departamentos.sql", "/dataset/funcionarios.sql" })
	public void detalharFuncionarioMustFailIdNaoExiste() {

		Funcionario funcionario = this.funcionarioService.detalharFuncionario(1L);

	}

	/**
	 * ================== EXCLUIR ===============================
	 */

	@Test
	@Sql({ "/dataset/truncate.sql", "/dataset/departamentos.sql", "/dataset/funcionarios.sql" })
	public void removerFuncionarioMustPass() {

		this.funcionarioService.removerFuncionario(1001);

		Funcionario funcionario = this.funcionarioRepository.findById(1001L).orElse(null);

		Assert.assertNull(funcionario);
	}

}

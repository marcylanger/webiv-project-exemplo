package com.springwebiv.model.entity;

import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.ManyToOne;
import javax.persistence.PrePersist;
import javax.persistence.PreUpdate;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.util.Assert;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.datatype.jsr310.deser.LocalDateDeserializer;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@Data
@EqualsAndHashCode(callSuper = true)
@NoArgsConstructor
@AllArgsConstructor
@Entity
public class Funcionario extends AbstractEntity implements Serializable {
	private static final long serialVersionUID = 1L;
	

	/**
	 * =========================== ATRIBUTOS
	 */
	
	@NotBlank
	private String nome;
	
	private BigDecimal salario;
	
	@Column(unique = true, nullable = false, length = 11)
	@NotBlank
	private String cpf;
	
	private LocalTime horaEntrada;

	private LocalTime horaSaida;
	
	private LocalDateTime dataDemissao;
	
	@NotNull
	private LocalDate dataNascimento;
	
	@NotNull
	@Enumerated( EnumType.ORDINAL )
	private CargoEnum cargo;
	
	@JsonIgnoreProperties("funcionarios")
	@ManyToOne(targetEntity = Departamento.class,
			fetch = FetchType.LAZY,
			optional = false)
	private Departamento departamento;
	
	
	
	/**
	 * =========================== MÉTODOS
	 */
	
	
	
	
	public Integer getIdade() {
		if(dataNascimento != null) {
			return LocalDate.now().getYear() - dataNascimento.getYear();
		}
		return 0;
		
	}
	
	@PrePersist
	@PreUpdate
	public void verificarIdade() {
		Assert.isTrue(this.getIdade() >= 16, "O funcionario deve ser maior de 16 anos");

	}
	
	
	

}


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
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.PrePersist;
import javax.persistence.PreUpdate;
import javax.persistence.Transient;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

import org.springframework.util.Assert;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Data
@EqualsAndHashCode
@ToString
@NoArgsConstructor
@AllArgsConstructor
@Entity
public class Funcionario implements Serializable {
	private static final long serialVersionUID = 1L;
	
	@Id
	@GeneratedValue
	private Long id;
	
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
	
	@Transient
	private Integer idade;
	
	public Integer getIdade() {
		if(dataNascimento != null) {
			return LocalDate.now().getYear() - dataNascimento.getYear();
		}else return this.idade;
		
	}
	
	@PrePersist
	@PreUpdate
	public void verificarIdade() {
		Assert.isTrue(this.getIdade() >= 16, "O funcionario deve ser maior de 16 anos");

	}
	
	
	

}


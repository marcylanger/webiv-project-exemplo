package com.springwebiv.entities;

import java.math.BigDecimal;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Data
@EqualsAndHashCode
@ToString
@NoArgsConstructor
public class Funcionario {

	private Long id;
	private String nome;
	private BigDecimal salario;

}


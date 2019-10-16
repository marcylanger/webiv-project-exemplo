package com.springwebiv.model.entity;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.OneToMany;
import javax.validation.constraints.NotBlank;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@Entity
@Data
@EqualsAndHashCode(callSuper = true)
@NoArgsConstructor
@AllArgsConstructor
public class Departamento extends AbstractEntity implements Serializable{/**
	 * 
	 */
	private static final long serialVersionUID = 665426621504404261L;

	@NotBlank
	private String nome;
	
	private String descricao;
	
	/* OrphanRemoval quer dizer que se excluirmos o departamento, se o funcionário
	 *  não estiver vinculado a outro departamento ele será excluído
	 * Enquanto que, cascade remove quer dizer que se eu remover o departamento os funcionários serão removidos*/
	@JsonIgnoreProperties("departamento")
	@OneToMany(targetEntity = Funcionario.class, cascade = {CascadeType.PERSIST, CascadeType.MERGE, CascadeType.REMOVE},
			fetch = FetchType.EAGER, mappedBy = "departamento", orphanRemoval = true)
	private List<Funcionario> funcionarios = new ArrayList<Funcionario>();
	
	
	public Departamento(Long id) {
		super.setId(id);
	}
}

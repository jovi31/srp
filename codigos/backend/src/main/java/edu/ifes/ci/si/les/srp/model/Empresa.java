package edu.ifes.ci.si.les.srp.model;

import java.io.Serializable;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode(of = {"id"})
public class Empresa implements Serializable {

	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer id;

	@Column(length = 30)
	@NotBlank(message = "Nome da empresa não informado")
	@Size(max = 30, message = "O nome da empresa deve possuir no máximo 30 caracteres")
	private String nome;

	@Column(length = 14, unique = true)
	@NotBlank(message = "CNPJ da empresa não informado")
	@Size(min = 14, max = 14, message = "O CNPJ da empresa deve possuir 14 dígitos")
	private String cnpj;

	@NotNull(message = "Uma empresa deve estar presente em ao menos 1 cidade")
	@ManyToMany
	@JoinTable(name = "empresa_cidade", 
		joinColumns = @JoinColumn(name = "empresa_id"), 
		inverseJoinColumns = @JoinColumn(name = "cidade_id")
	)
	private List<Cidade> cidade;

}

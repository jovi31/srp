package edu.ifes.ci.si.les.srp.model;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode(of = {"sigla"})
public class UF implements Serializable {

	private static final long serialVersionUID = 1L;

	@Id
	@Column(length = 2)
	@NotBlank(message = "Sigla da UF não informada")
	@Size(min = 2, max = 2, message = "Sigla da UF deve ter 2 letras")
	private String sigla;

	@Column(length = 30)
	@NotBlank(message = "Nome da UF não informado")
	@Size(min = 2, max = 30, message = "Nome da UF deve ter entre 2 e 30 letras")
	private String nome;

}

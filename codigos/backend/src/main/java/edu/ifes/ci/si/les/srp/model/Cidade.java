package edu.ifes.ci.si.les.srp.model;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
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
public class Cidade implements Serializable {

	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer id;

	@Column(length = 30)
	@NotBlank(message = "Nome da cidade não informado")
	@Size(min = 2, max = 30, message = "Nome da cidade deve ter entre 2 e 30 letras")
	private String nome;

	@NotNull(message = "UF da cidade não informada")
	@ManyToOne
	@JoinColumn(name = "uf_sigla")
	private UF uF;

}

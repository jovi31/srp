package edu.ifes.ci.si.les.srp.model;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Entity;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonProperty.Access;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode(of = {"id"})
public class Usuario implements Serializable {

	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer id;

	@Column(length = 30)
	@NotBlank(message = "Nome do usuário não informado")
	@Size(max = 30, message = "O nome do usuário deve possuir no máximo 30 caracteres")
	private String nome;

	@Column(length = 11, unique = true)
	@NotBlank(message = "CPF não informado")
	@Size(min = 11, max = 11, message = "CPF incompleto")
	private String cpf;

	@Column(length = 2)
	@NotBlank(message = "DDD não informado")
	@Size(min = 2, max = 2, message = "O DDD deve possuir 2 digitos")
	private String ddd;

	@Column(length = 9)
	@NotBlank(message = "Número de telefone não informado")
	@Size(min = 8, max = 9, message = "O número de telefone deve ter entre 8 e 9 dígitos")
	private String telefone;

	@NotBlank(message = "Endereço de email não informado")
	@Size(min = 7, message = "O endereço de email deve possuir no mínimo 7 caracteres")
	private String email;

	@JsonProperty(access = Access.WRITE_ONLY)
	@Column(length = 20)
	@NotBlank(message = "Senha não informada")
	@Size(min = 8, max = 20, message = "A senha deve possuir entre 8 e 20 caracteres")
	private String senha;
	
	@NotNull
	private Boolean admin;

}

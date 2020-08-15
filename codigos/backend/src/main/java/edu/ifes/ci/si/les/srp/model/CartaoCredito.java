package edu.ifes.ci.si.les.srp.model;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonProperty.Access;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@Embeddable
@Data
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
public class CartaoCredito implements Serializable {

	private static final long serialVersionUID = 1L;

	// criar uma classe Bandeira
	@EqualsAndHashCode.Include
	@Min(value = 0)
	@Max(value = 5)
	@NotNull(message = "Bandeira do cartão não informada ou inválida")
	private Integer bandeira;

	@Column(length = 30)
	@NotBlank(message = "Titular do cartão de crédito não informado")
	@Size(max = 30, message = "O campo titular deve ser preenchido com até 30 dígitos")
	private String titular;

	@JsonProperty(access = Access.WRITE_ONLY)
	@EqualsAndHashCode.Include
	@Column(length = 19)
	@NotBlank(message = "Número do cartão de crédito não informado")
	@Size(min = 13, max = 19, message = "O número do cartão de crédito deve ter entre 13 e 19 dígitos")
	private String numero;

	@Column(length = 2)
	@NotBlank(message = "Mês de expiração não informado")
	@Size(min = 2, max = 2, message = "O campo mês de expiração deve possuir 2 dígitos")
	private String mesExp;

	@Min(value = 2020)
	@Max(value = 2034)
	@NotNull(message = "Ano de expiração não informado")
	private Integer anoExp;

	@JsonProperty(access = Access.WRITE_ONLY)
	@Column(length = 4)
	@NotBlank(message = "CVV não informado")
	@Size(min = 3, max = 4, message = "O CVV deve possuir 3 ou 4 dígitos")
	private String cvv;

}

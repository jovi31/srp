package edu.ifes.ci.si.les.srp.model;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
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
public class Conta implements Serializable {

	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer id;

	@Column(length = 30)
	@NotBlank(message = "O campo nome deve ser preenchido")
	@Size(max = 30, message = "O campo nome deve possuir no máximo 30 caracteres")
	private String nome;

	@Column(length = 11)
	@NotBlank(message = "CPF não informado")
	@Size(min = 11, max = 11, message = "CPF incompleto")
	private String cpf;

	@Column(length = 3)
	@NotBlank(message = "Código do banco não informado")
	@Size(max = 3, message = "O código do banco não deve ultrapassar 3 dígitos")
	private String banco;

	@Column(length = 4)
	@NotBlank(message = "Código da agência não informado")
	@Size(max = 4, message = "O código da agência não deve ultrapassar 4 dígitos")
	private String agencia;

	@Column(length = 15)
	@NotBlank(message = "Número da conta não informado")
	@Size(max = 15, message = "O número da conta deve ter até 15 dígitos")
	private String conta;

	@Enumerated(EnumType.ORDINAL)
	@NotNull(message = "O tipo da conta deve ser informado")
	private TipoConta tipo;
	
	@NotNull(message = "Cliente não informado")
	@ManyToOne
	@JoinColumn(name = "cliente_id")
	private Usuario cliente;

}

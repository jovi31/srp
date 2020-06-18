package edu.ifes.ci.si.les.srp.model;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
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
public class CartaoInteligente implements Serializable {

	private static final long serialVersionUID = 1L;

	@EmbeddedId
	private CartaoInteligentePK id;

	@Column(length = 30)
	@NotBlank(message = "Nome do cartão inteligente não informado")
	@Size(max = 30, message = "O nome do cartão inteligente não deve ultrapassar 30 caracteres")
	private String nome;
	
	@JsonProperty(access = Access.READ_ONLY)
	private Float carteira;

	@ManyToOne
	@JoinColumn(name = "cliente_id")
	@NotNull(message = "Cliente não informado")
	private Usuario cliente;

}

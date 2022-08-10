package edu.ifes.ci.si.les.srp.model;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@Embeddable
@Data
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode(of = {"numero", "empresa"})
public class CartaoInteligentePK implements Serializable {
	
	private static final long serialVersionUID = 1L;
	
	@Column(length = 20, name = "ci_numero")
	@NotBlank(message = "Número do cartão inteligente não informado")
	@Size(max = 20, message = "O número do cartão inteligente não deve ultrapassar 20 caracteres")
	private String numero;
	
	@ManyToOne
	@JoinColumn(name = "empresa_id")
	@NotNull(message = "Empresa não informada")
	private Empresa empresa;
}

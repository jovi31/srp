package edu.ifes.ci.si.les.srp.model;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Embedded;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinColumns;
import javax.persistence.ManyToOne;
import javax.validation.Valid;
import javax.validation.constraints.Digits;
import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;

import com.fasterxml.jackson.annotation.JsonFormat;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode(of = {"id"})
public class Recarga implements Serializable {

	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer id;

	@JsonFormat(pattern = "yyyy-MM-dd")
	private Date data;

	@Enumerated(EnumType.ORDINAL)
	private Status status;

	@Min(value = 1, message = "O valor da recarga deve ser maior que 0")
	@Max(value = 200, message = "O valor da recarga não pode ultrapassar R$ 200,00")
	@NotNull(message = "O valor da recarga deve ser preenchido")
	@Digits(integer = 3, fraction = 2, message = "Valor inválido")
	private Float valor;

	@ManyToOne
	@JoinColumns(value = {
			@JoinColumn(name = "ci_numero", referencedColumnName = "ci_numero"),
			@JoinColumn(name = "empresa_id", referencedColumnName = "empresa_id")
	})
	// @JoinColumn(name = "ci_numero")
	@NotNull(message = "Cartão inteligente não informado")
	private CartaoInteligente cartaoInteligente;

	@Valid
	@Embedded
	@NotNull(message = "Cartão de crédito não informado")
	private CartaoCredito cartaoCredito;

}

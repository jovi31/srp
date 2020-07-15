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
public class Agendamento implements Serializable {

	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer id;

	@Min(value = 1, message = "O valor do agendamento deve ser maior que 0")
	@Max(value = 200, message = "O valor do agendamento não pode ultrapassar R$ 200,00")
	@NotNull(message = "O valor do agendamento deve ser preenchido")
	@Digits(integer = 3, fraction = 2, message = "O valor do agendamento deve ser preenchido com dígitos")
	private Float valor;

	@NotNull
	@JsonFormat(pattern = "yyyy-MM-dd")
	// @FutureOrPresent
	private Date dataInicio;

	@Min(value = 1)
	@Max(value = 17)
	@NotNull(message = "Período não informado")
	private Integer periodo;

	@Enumerated(EnumType.ORDINAL)
	@NotNull
	private TipoPeriodo tipoPeriodo;

	@ManyToOne
	@JoinColumns(value = {
			@JoinColumn(name = "ci_numero", referencedColumnName = "ci_numero"),
			@JoinColumn(name = "empresa_id", referencedColumnName = "empresa_id")
	})
	// @JoinColumn(name = "ci_numero")
	@NotNull(message = "Cartão inteligente não informado")
	private CartaoInteligente cartaoInteligente;

	@Embedded
	private CartaoCredito cartaoCredito;

}

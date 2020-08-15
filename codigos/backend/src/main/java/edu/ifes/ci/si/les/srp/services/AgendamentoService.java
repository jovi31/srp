package edu.ifes.ci.si.les.srp.services;

import java.util.Date;
import java.util.List;
import java.util.NoSuchElementException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;

import edu.ifes.ci.si.les.srp.model.Agendamento;
import edu.ifes.ci.si.les.srp.model.CartaoInteligente;
import edu.ifes.ci.si.les.srp.model.CartaoInteligentePK;
import edu.ifes.ci.si.les.srp.repositories.AgendamentoRepository;
import edu.ifes.ci.si.les.srp.repositories.CartaoInteligenteRepository;
import edu.ifes.ci.si.les.srp.services.exceptions.BusinessRuleException;
import edu.ifes.ci.si.les.srp.services.exceptions.ConstraintException;
import edu.ifes.ci.si.les.srp.services.exceptions.DataIntegrityException;
import edu.ifes.ci.si.les.srp.services.exceptions.ObjectNotFoundException;

@Service
public class AgendamentoService {
	
	@Autowired
	private AgendamentoRepository repository;
	@Autowired
	private CartaoInteligenteRepository ciRepository;
	
	public Agendamento findById(Integer id) {
		try {
			Agendamento obj = repository.findById(id).get();
			return obj;
		} catch(NoSuchElementException e) {
			throw new ObjectNotFoundException("O Agendamento de id " + id + " não foi encontrado!");
		}
	}
	
	public List<Agendamento> findAll() {
		return repository.findAll();
	}
	
	public Agendamento insert(Agendamento obj, Integer userId, Boolean admin) {
		CartaoInteligente ci = ciRepository.findById(obj.getCartaoInteligente().getId()).get();
		
		if (!admin) {
			Integer ciUserId = ci.getCliente().getId();
			if (userId != ciUserId) {
				throw new BusinessRuleException("O cartão inteligente deve pertencer ao usuário.");
			}
		}

		if (obj.getValor() > 200)
			throw new ConstraintException("O valor do agendamento não pode ultrapassar R$ 200,00");

		obj.setId(null);

		CartaoInteligentePK ciPK = ci.getId();
		Integer empresaId = ciPK.getEmpresa().getId();
		String ciNumero = ciPK.getNumero();

		Integer qtdAgendamentos = repository.qtdAgendamentosPorCartao(empresaId, ciNumero);
		if (qtdAgendamentos == 3)
			throw new BusinessRuleException("Não é possível registrar mais de 3 agendamentos para um cartão.");

		Integer periodo = obj.getPeriodo();
		Integer tipoPeriodo = obj.getTipoPeriodo().ordinal();

		if (tipoPeriodo == 1)
			periodo = (int) (periodo * ((float) 7/30));

		if (periodo > 4)
			throw new BusinessRuleException("O período do agendamento não pode ultrapassar 4 meses.");

		// Validação da data de início
		Date now = new Date();
		Date dataInicial = obj.getDataInicio();
		if(now.compareTo(dataInicial) >= 0) {
			throw new BusinessRuleException("A data inicial do agendamento precisa ser uma data no futuro.");
		}

		try {
			return repository.save(obj);
		} catch (DataIntegrityViolationException e) {
			throw new DataIntegrityException("Campo(s) obrigatório(s) do Agendamento não foi(foram) preenchido(s)");
		}

	}
	
	public Agendamento update(Agendamento obj, Integer userId, Boolean admin) {
		Agendamento old = findById(obj.getId());
		CartaoInteligente ci = old.getCartaoInteligente();

		if (!admin) {
			Integer ciUserId = ci.getCliente().getId();
			if (userId != ciUserId) {
				throw new BusinessRuleException("O agendamento deve pertencer ao usuário.");
			}
		}

		Float valor = obj.getValor();
		if (valor != null) {
			if (valor < 1) {
				throw new ConstraintException("O valor do agendamento deve ser maior ou igual a R$ 1,00.");
			} else {
				if (valor > 200) {
					throw new ConstraintException("O valor do agendamento não pode ultrapassar R$ 200,00.");
				}
			}
		} else {
			throw new ConstraintException("O valor do agendamento deve ser preenchido.");
		}

		obj.setDataInicio(old.getDataInicio());
		obj.setPeriodo(old.getPeriodo());
		obj.setTipoPeriodo(old.getTipoPeriodo());
		obj.setCartaoInteligente(old.getCartaoInteligente());
		obj.setCartaoCredito(old.getCartaoCredito());

		try {
			return repository.save(obj);
		} catch (DataIntegrityViolationException e) {
			throw new DataIntegrityException("Campo(s) obrigatório(s) do Agendamento não foi(foram) preenchido(s)");
		}

	}
	
	public void delete(Integer id, Integer userId, Boolean admin) {
		Agendamento agendamento = findById(id);
		CartaoInteligente ci = agendamento.getCartaoInteligente();
		
		if (!admin) {
			Integer ciUserId = ci.getCliente().getId();
			if (userId != ciUserId) {
				throw new BusinessRuleException("O agendamento deve pertencer ao usuário.");
			}
		}
		
		try {
			repository.deleteById(id);
		} catch (DataIntegrityViolationException e) {
			throw new DataIntegrityException("Não é possível deletar este agendamento.");
		}
	}
	
	public List<Agendamento> findByCartaoInteligente(CartaoInteligente ci, Integer userId, Boolean admin) {
		if (!ciRepository.existsById(ci.getId()))
			throw new DataIntegrityException("O cartão inteligente informado não existe");

		ci = ciRepository.findById(ci.getId()).get();

		if (!admin) {
			Integer ciUserId = ci.getCliente().getId();
			if (userId != ciUserId) {
				throw new BusinessRuleException("O cartão deve pertencer ao usuário.");
			}
		}

		return repository.findByCartaoInteligente(ci);

	}

}

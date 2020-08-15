package edu.ifes.ci.si.les.srp.services;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.NoSuchElementException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;

import edu.ifes.ci.si.les.srp.model.CartaoInteligente;
import edu.ifes.ci.si.les.srp.model.Conta;
import edu.ifes.ci.si.les.srp.model.Saque;
import edu.ifes.ci.si.les.srp.model.Status;
import edu.ifes.ci.si.les.srp.repositories.SaqueRepository;
import edu.ifes.ci.si.les.srp.services.exceptions.BusinessRuleException;
import edu.ifes.ci.si.les.srp.services.exceptions.DataIntegrityException;
import edu.ifes.ci.si.les.srp.services.exceptions.ObjectNotFoundException;

@Service
public class SaqueService {
	
	@Autowired
	private SaqueRepository repository;
	@Autowired
	private CartaoInteligenteService ciService;
	@Autowired
	private ContaService contaService;
	
	public Saque findById(Integer id) {
		try {
			Saque obj = repository.findById(id).get();
			return obj;
		} catch (NoSuchElementException e) {
			throw new ObjectNotFoundException("Saque de id " + id + " não encontrado!");
		}
	}
	
	public List<Saque> findAll() {
		return repository.findAll();
	}
	
	public Saque insert(Saque obj, Integer userId, Boolean admin) {
		CartaoInteligente ci = ciService.findById(obj.getCartaoInteligente().getId());
		Conta conta = contaService.findById(obj.getConta().getId());
		
		if (!admin) {
			Integer ciUserId = ci.getCliente().getId();
			Integer contaUserId = conta.getCliente().getId();
			if (userId != ciUserId || userId != contaUserId) {
				throw new BusinessRuleException("A conta e o cartão devem pertencer ao usuário.");
			}
		}
		
		Date now = new Date();
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		String dia = sdf.format(now);
		String t1 = dia + " 00:00:00";
		String t2 = dia + " 23:59:59";
		
		List<Saque> saquesNoDia = repository.findSaquesBetweenTimestamps(t1, t2);
		if (!saquesNoDia.isEmpty()) {
			throw new BusinessRuleException("Não é permitido a realização de mais de um saque por dia.");
		}
		
		Float valorSaque = obj.getValor();
		Float ciCarteira = ci.getCarteira();
		
		if (valorSaque <= ciCarteira) {
			ciCarteira -= valorSaque;
			ci.setCarteira(ciCarteira);
		} else {
			throw new BusinessRuleException("O valor na carteira é insuficiente para a realização do saque.");
		}
		
		obj.setId(null);
		obj.setStatus(Status.PENDENTE);
		obj.setData(now);
		
		try {
			obj = repository.save(obj);
			ciService.update(ci);
			return obj;
		} catch (DataIntegrityViolationException e) {
			throw new DataIntegrityException("Campo(s) obrigatório(s) do saque não foi(foram) preenchido(s).");
		}
	}
	
	public Saque update(Saque obj) {
		Saque old = findById(obj.getId());
		
		ciService.findById(obj.getCartaoInteligente().getId());
		contaService.findById(obj.getConta().getId());
		
		if(obj.getData() == null)
			obj.setData(old.getData());
		
		if(obj.getStatus() == null)
			obj.setStatus(old.getStatus());
		
		try {
			obj = repository.save(obj);
			return obj;
		} catch (DataIntegrityViolationException e) {
			throw new DataIntegrityException("Campo(s) obrigatório(s) do saque não foi(foram) preenchido(s).");
		}
	}
	
	public void delete(Integer id) {
		findById(id);
		try {
			repository.deleteById(id);
		} catch (DataIntegrityViolationException e) {
			throw new DataIntegrityException("Não é possível deletar este saque.");
		}
	}
	
	public List<Saque> findByCliente(Integer clienteId) {
		return repository.findByCliente(clienteId);
	}
	
}

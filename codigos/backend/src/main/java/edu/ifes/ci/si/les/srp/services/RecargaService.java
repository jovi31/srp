package edu.ifes.ci.si.les.srp.services;

import java.util.Date;
import java.util.List;
import java.util.NoSuchElementException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;

import edu.ifes.ci.si.les.srp.model.CartaoInteligente;
import edu.ifes.ci.si.les.srp.model.CartaoInteligentePK;
import edu.ifes.ci.si.les.srp.model.Recarga;
import edu.ifes.ci.si.les.srp.model.Status;
import edu.ifes.ci.si.les.srp.repositories.CartaoInteligenteRepository;
import edu.ifes.ci.si.les.srp.repositories.RecargaRepository;
import edu.ifes.ci.si.les.srp.repositories.SaqueRepository;
import edu.ifes.ci.si.les.srp.services.exceptions.BusinessRuleException;
import edu.ifes.ci.si.les.srp.services.exceptions.ConstraintException;
import edu.ifes.ci.si.les.srp.services.exceptions.DataIntegrityException;
import edu.ifes.ci.si.les.srp.services.exceptions.ObjectNotFoundException;

@Service
public class RecargaService {
	
	@Autowired
	private RecargaRepository repository;
	@Autowired
	private CartaoInteligenteRepository ciRepository;
	@Autowired
	private SaqueRepository saqueRepository;
	
	public Recarga findById(Integer id) {
		try {
			Recarga obj = repository.findById(id).get();
			return obj;
		} catch(NoSuchElementException e) {
			throw new ObjectNotFoundException("A Recarga de id " + id + " não foi encontrada!");
		}
	}
	
	public List<Recarga> findAll() {
		return repository.findAll();
	}
	
	public Recarga insert(Recarga obj, Integer userId, Boolean admin) {
		CartaoInteligente ci = ciRepository.findById(obj.getCartaoInteligente().getId()).get();
		
		if (!admin) {
			Integer ciUserId = ci.getCliente().getId();
			if (userId != ciUserId) {
				throw new BusinessRuleException("O cartão deve pertencer ao usuário.");
			}
		}
		
		if (obj.getValor() > 200)
			throw new ConstraintException("O valor da recarga não pode ultrapassar R$ 200,00");
		
		obj.setId(null);
		CartaoInteligentePK ciPK = ci.getId();
		Integer empresaId = ciPK.getEmpresa().getId();
		String ciNumero = ciPK.getNumero();
		
		Integer qtdRecargasPendentes = repository.qtdRecargasPendentes(empresaId, ciNumero);
		
		if (qtdRecargasPendentes == 20) {
			throw new BusinessRuleException("O cartão inteligente possui 20 recargas pendentes.");
		}
		
		Float somaRecargasPendentes = repository.somaRecargasPendentes(empresaId, ciNumero);
		if (somaRecargasPendentes == null) {
			somaRecargasPendentes = (float) 0;
		}
		
		Float total = ci.getCarteira() + somaRecargasPendentes + obj.getValor();
		
		if (total > 200) {
			throw new BusinessRuleException("O valor informado excede o limite da carteira. Valor máximo que pode ser adicionado: R$ " + (obj.getValor() - (total - 200)) + ".");
		}
		
		Integer qtdSaquesPendentes = saqueRepository.qtdSaquesPendentes(empresaId, ciNumero);
		
		if (qtdSaquesPendentes > 0) {
			throw new BusinessRuleException("A recarga não pode ser realizada pois existe um saque pendente associado ao cartão.");
		}
		
		Date date = new Date();
		obj.setData(date);
		obj.setStatus(Status.PENDENTE);
		
		try {
			return repository.save(obj);
		} catch (DataIntegrityViolationException e) {
			throw new DataIntegrityException("Campo(s) obrigatório(s) da Recarga não foi(foram) preenchido(s)");
		}
	}
	
	public Recarga update(Recarga obj) {
		Recarga old = findById(obj.getId());
		
		if (!ciRepository.existsById(obj.getCartaoInteligente().getId()))
			throw new DataIntegrityException("O cartão inteligente informado não existe");
		
		if(obj.getData() == null)
			obj.setData(old.getData());
		
		if(obj.getStatus() == null)
			obj.setStatus(old.getStatus());
		
		try {
			return repository.save(obj);
		} catch(DataIntegrityViolationException e) {
			throw new DataIntegrityException("Campo(s) obrigatório(s) da Recarga não foi(foram) preenchido(s)");
		}
	}
	
	public void delete(Integer id) {
		findById(id);
		try {
			repository.deleteById(id);
		} catch(DataIntegrityViolationException e) {
			throw new DataIntegrityException("Não é possível deletar esta recarga.");
		}
	}
	
	public List<Recarga> findByCliente(Integer clienteId) {
		return repository.findByCliente(clienteId);
	}
	
	public List<?> findTotaisAndQuantidadesRecargasOfEmpresasByPeriodo(String inicio, String termino) {
		return repository.findTotaisAndQuantidadesRecargasOfEmpresasByPeriodo(inicio, termino);
	}
	
	public List<?> findRecargasByClienteAndPeriodo(Integer cliente, String inicio, String termino) {
		return repository.findRecargasByClienteAndPeriodo(cliente, inicio, termino);
	}
	
}

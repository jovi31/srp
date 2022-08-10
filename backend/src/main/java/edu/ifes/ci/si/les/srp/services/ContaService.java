package edu.ifes.ci.si.les.srp.services;

import java.util.List;
import java.util.NoSuchElementException;

import org.hibernate.exception.ConstraintViolationException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;

import edu.ifes.ci.si.les.srp.model.Conta;
import edu.ifes.ci.si.les.srp.model.Usuario;
import edu.ifes.ci.si.les.srp.repositories.ContaRepository;
import edu.ifes.ci.si.les.srp.services.exceptions.ConstraintException;
import edu.ifes.ci.si.les.srp.services.exceptions.DataIntegrityException;
import edu.ifes.ci.si.les.srp.services.exceptions.ObjectNotFoundException;

@Service
public class ContaService {
	
	@Autowired
	private ContaRepository repository;
	
	public Conta findById(Integer id) {
		try {
			Conta obj = repository.findById(id).get();
			return obj;
		} catch (NoSuchElementException e) {
			throw new ObjectNotFoundException("Objeto não encontrado! Id: " + id + ", Tipo: " + Conta.class.getName());
		}
	}
	
	public List<Conta> findAll() {
		return repository.findAll();
	}
	
	public Conta insert(Conta obj) {
		obj.setId(null);
		if(obj.getCliente().getId() == null)
			throw new ConstraintException("Id do cliente não informado");
		try {
			return repository.save(obj);
		} catch (DataIntegrityViolationException e) {
			Throwable cause = e.getCause();
			if (ConstraintViolationException.class.equals(cause.getClass()))
				throw new ConstraintException("O usuário informado não existe");
			throw new DataIntegrityException("Campo(s) obrigatório(s) da Conta não foi(foram) preenchido(s)");
		}
	}
	
	public Conta update(Conta obj) {
		Conta old = findById(obj.getId());
		obj.setCliente(old.getCliente()); // Não permite a alteração do cliente da conta
		try {
			return repository.save(obj);
		} catch (DataIntegrityViolationException e) {
			throw new DataIntegrityException("Campo(s) obrigatório(s) da Conta não foi(foram) preenchido(s)");
		}
	}
	
	public void delete(Integer id) {
		findById(id);
		try {
			repository.deleteById(id);
		} catch (DataIntegrityViolationException e) {
			throw new DataIntegrityException("Não é possível deletar uma Conta que possui saques!");
		}
	}
	
	public List<Conta> findByCliente(Usuario cliente) {
		List<Conta> list = repository.findByCliente(cliente);
		return list;
	}
	
}

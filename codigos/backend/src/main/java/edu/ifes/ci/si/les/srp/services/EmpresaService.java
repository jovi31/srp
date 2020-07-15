package edu.ifes.ci.si.les.srp.services;

import java.util.List;
import java.util.NoSuchElementException;

import org.hibernate.exception.ConstraintViolationException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;

import edu.ifes.ci.si.les.srp.model.Empresa;
import edu.ifes.ci.si.les.srp.repositories.EmpresaRepository;
import edu.ifes.ci.si.les.srp.services.exceptions.ConstraintException;
import edu.ifes.ci.si.les.srp.services.exceptions.DataIntegrityException;
import edu.ifes.ci.si.les.srp.services.exceptions.ObjectNotFoundException;

@Service
public class EmpresaService {
	
	@Autowired
	private EmpresaRepository repository;
	
	public Empresa findById(Integer id) {
		try {
			Empresa obj = repository.findById(id).get();
			return obj;
		} catch (NoSuchElementException e) {
			throw new ObjectNotFoundException("Objeto não encontrado! Id: " + id + ", Tipo: " + Empresa.class.getName());
		}
	}
	
	public List<Empresa> findAll() {
		return repository.findAll();
	}
	
	public Empresa insert(Empresa obj) {
		obj.setId(null);
		try {
			return repository.save(obj);
		} catch (DataIntegrityViolationException e) {
			Throwable cause = e.getCause();
			if (ConstraintViolationException.class.equals(cause.getClass())) {
				throw new ConstraintException("Existe uma empresa com o mesmo CNPJ");
			}
			throw new DataIntegrityException("Campo(s) obrigatório(s) da Empresa não foi(foram) preenchido(s)");
		}
	}
	
	public Empresa update(Empresa obj) {
		findById(obj.getId());
		try {
			return repository.save(obj);
		} catch (DataIntegrityViolationException e) {
			Throwable cause = e.getCause();
			if (ConstraintViolationException.class.equals(cause.getClass()))
				throw new ConstraintException("Existe uma empresa com o mesmo CNPJ");
			throw new DataIntegrityException("Campo(s) obrigatório(s) da Empresa não foi(foram) preenchido(s)");
		}
	}
	
	public void delete(Integer id) {
		findById(id);
		try {
			repository.deleteById(id);
		} catch (DataIntegrityViolationException e) {
			throw new DataIntegrityException("Não é possível remover uma Empresa que possui cartões!");
		}
	}
	
}

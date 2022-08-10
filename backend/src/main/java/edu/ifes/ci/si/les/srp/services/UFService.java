package edu.ifes.ci.si.les.srp.services;

import java.util.List;
import java.util.NoSuchElementException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;

import edu.ifes.ci.si.les.srp.model.UF;
import edu.ifes.ci.si.les.srp.repositories.UFRepository;
import edu.ifes.ci.si.les.srp.services.exceptions.DataIntegrityException;
import edu.ifes.ci.si.les.srp.services.exceptions.ObjectAlreadyExistsException;
import edu.ifes.ci.si.les.srp.services.exceptions.ObjectNotFoundException;

@Service
public class UFService {
	
	@Autowired
	private UFRepository repository;
	
	public UF findById(String id) {
		try {
			UF obj = repository.findById(id).get();
			return obj;
		} catch(NoSuchElementException e) {
			throw new ObjectNotFoundException("A UF com sigla " + id + " não foi encontrada!");
		}
	}
	
	public List<UF> findAll() {
		return repository.findAll();
	}
	
	public UF insert(UF obj) {
		if(repository.existsById(obj.getSigla()))
			throw new ObjectAlreadyExistsException("A UF com sigla " + obj.getSigla() + " já foi inserida");
		
		try {
			return repository.save(obj);
		} catch (DataIntegrityViolationException e) {
			throw new DataIntegrityException("Campo(s) obrigatório(s) da UF não foi(foram) preenchido(s)");
		}
	}
	
	public UF update(UF obj) {
		findById(obj.getSigla());
		try {
			return repository.save(obj);
		} catch (DataIntegrityViolationException e) {
			throw new DataIntegrityException("Campo(s) obrigatório(s) da UF não foi(foram) preenchido(s)");
		}
	}
	
	public void delete(String id) {
		findById(id);
		try {
			repository.deleteById(id);
		} catch (DataIntegrityViolationException e) {
			throw new DataIntegrityException("Não é possível deletar uma UF que possui cidades!");
		}
	}
	
}

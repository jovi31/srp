package edu.ifes.ci.si.les.srp.services;

import java.util.List;
import java.util.NoSuchElementException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;

import edu.ifes.ci.si.les.srp.model.Cidade;
import edu.ifes.ci.si.les.srp.model.UF;
import edu.ifes.ci.si.les.srp.repositories.CidadeRepository;
import edu.ifes.ci.si.les.srp.services.exceptions.DataIntegrityException;
import edu.ifes.ci.si.les.srp.services.exceptions.ObjectNotFoundException;

@Service
public class CidadeService {
	
	@Autowired
	private CidadeRepository repository;
	
	public Cidade findById(Integer id) {
		try {
			Cidade obj = repository.findById(id).get();
			return obj;
		} catch(NoSuchElementException e) {
			throw new ObjectNotFoundException("Objeto não encontrado! Id: " + id + ", Tipo: " + Cidade.class.getName());
		}
	}
	
	public List<Cidade> findByUf(UF uf) {
		return repository.findByUf(uf);
	}
	
	public List<Cidade> findAll() {
		return repository.findAll();
	}
	
	public Cidade insert(Cidade obj) {
		obj.setId(null);
		try {
			return repository.save(obj);
		} catch (DataIntegrityViolationException e) {
			throw new DataIntegrityException("Campo(s) obrigatório(s) da Cidade não foi(foram) preenchido(s)");
		}
	}
	
	public Cidade update(Cidade obj) {
		findById(obj.getId());
		try {
			return repository.save(obj);
		} catch (DataIntegrityViolationException e) {
			throw new DataIntegrityException("Campo(s) obrigatório(s) da Cidade não foi(foram) preenchido(s)");
		}
	}
	
	public void delete(Integer id) {
		findById(id);
		try {
			repository.deleteById(id);
		} catch (DataIntegrityViolationException e) {
			throw new DataIntegrityException("Não é possível deletar uma Cidade que possui empresas!");
		}
	}
	
}

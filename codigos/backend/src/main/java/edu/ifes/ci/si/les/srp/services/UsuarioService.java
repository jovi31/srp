package edu.ifes.ci.si.les.srp.services;

import java.util.List;
import java.util.NoSuchElementException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;

import edu.ifes.ci.si.les.srp.model.Usuario;
import edu.ifes.ci.si.les.srp.repositories.UsuarioRepository;
import edu.ifes.ci.si.les.srp.services.exceptions.ConstraintException;
import edu.ifes.ci.si.les.srp.services.exceptions.DataIntegrityException;
import edu.ifes.ci.si.les.srp.services.exceptions.ObjectNotFoundException;

@Service
public class UsuarioService {
	
	@Autowired
	private UsuarioRepository repository;
	
	public Usuario findById(Integer id) {
		try {
			return repository.findById(id).get();
		} catch (NoSuchElementException e) {
			throw new ObjectNotFoundException("Objeto não encontrado! Id: " + id + ", Tipo: " + Usuario.class.getName());
		}
	}
	
	public List<Usuario> findAll() {
		return repository.findAll();
	}
	
	public Usuario insert(Usuario obj) {
		if(repository.existsByCpf(obj.getCpf()))
			throw new ConstraintException("Já existe um usuário cadastrado com esse CPF");
		if(repository.existsByEmail(obj.getEmail()))
			throw new ConstraintException("Já existe um usuário cadastrado com esse E-mail");
		
		obj.setId(null);
		try {
			return repository.save(obj);
		} catch (DataIntegrityViolationException e) {
			throw new DataIntegrityException("Campo(s) obrigatório(s) do Usuário não foi(foram) preenchido(s)");
		}
	}
	
	public Usuario update(Usuario obj) {
		Usuario old = findById(obj.getId());
		
		if(!(old.getCpf().equals(obj.getCpf())))
			if (repository.existsByCpf(obj.getCpf()))
				throw new ConstraintException("Já existe um usuário cadastrado com esse CPF");
		if(!(old.getEmail().equals(obj.getEmail())))
			if (repository.existsByEmail(obj.getEmail()))
				throw new ConstraintException("Já existe um usuário cadastrado com esse E-mail");
		
		try {
			return repository.save(obj);
		} catch (DataIntegrityViolationException e) {
			throw new DataIntegrityException("Campo(s) obrigatório(s) do Usuário não foi(foram) preenchido(s)");
		}
	}
	
	public void delete(Integer id) {
		findById(id);
		try {
			repository.deleteById(id);
		} catch (DataIntegrityViolationException e) {
			throw new DataIntegrityException("Não é possível deletar um usuário que possui cartões ou contas");
		}
	}
	
}

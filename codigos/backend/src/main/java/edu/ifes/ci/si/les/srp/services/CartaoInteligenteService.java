package edu.ifes.ci.si.les.srp.services;

import java.util.List;
import java.util.NoSuchElementException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;

import edu.ifes.ci.si.les.srp.model.CartaoInteligente;
import edu.ifes.ci.si.les.srp.model.CartaoInteligentePK;
import edu.ifes.ci.si.les.srp.model.Empresa;
import edu.ifes.ci.si.les.srp.model.Usuario;
import edu.ifes.ci.si.les.srp.repositories.CartaoInteligenteRepository;
import edu.ifes.ci.si.les.srp.repositories.EmpresaRepository;
import edu.ifes.ci.si.les.srp.repositories.UsuarioRepository;
import edu.ifes.ci.si.les.srp.services.exceptions.ConstraintException;
import edu.ifes.ci.si.les.srp.services.exceptions.DataIntegrityException;
import edu.ifes.ci.si.les.srp.services.exceptions.ObjectNotFoundException;

@Service
public class CartaoInteligenteService {
	
	@Autowired
	private CartaoInteligenteRepository repository;
	@Autowired
	private UsuarioRepository usuarioRepository;
	@Autowired
	private EmpresaRepository empresaRepository;
	
	public CartaoInteligente findById(CartaoInteligentePK id) {
		try {
			CartaoInteligente obj = repository.findById(id).get();
			return obj;
		} catch (NoSuchElementException e) {
			throw new ObjectNotFoundException("Objeto não encontrado! Id: " + id + ", Tipo: " + CartaoInteligente.class.getName());
		}
	}
	
	public List<CartaoInteligente> findAll() {
		return repository.findAll();
	}
	
	public CartaoInteligente insert(CartaoInteligente obj) {
		if(repository.existsById(obj.getId()))
			throw new ConstraintException("Esse cartão já foi cadastrado no sistema");
		
		Usuario cliente = obj.getCliente();
		if(!usuarioRepository.existsById(cliente.getId()))
			throw new ObjectNotFoundException("Usuário não encontrado! Id: " + cliente.getId() + ", Tipo: " + Usuario.class.getName());
		
		Empresa empresa = obj.getId().getEmpresa();
		if(!empresaRepository.existsById(empresa.getId()))
			throw new ObjectNotFoundException("Empresa não encontrada! Id: " + empresa.getId() + ", Tipo: " + Empresa.class.getName());
		
		obj.setCarteira((float) 0);
		
		try {
			return repository.save(obj);
		} catch (DataIntegrityViolationException e) {
			throw new DataIntegrityException("Campo(s) obrigatório(s) do Cartão não foi(foram) preenchido(s)");
		}
	}
	
	public CartaoInteligente update(CartaoInteligente obj) {
		CartaoInteligente old = findById(obj.getId());
		obj.setCliente(old.getCliente()); // Não permite a alteração do usuário do cartão
		
		if (obj.getCarteira() == null)
			obj.setCarteira(old.getCarteira());
		
		try {
			return repository.save(obj);
		} catch (DataIntegrityViolationException e) {
			throw new DataIntegrityException("Campo(s) obrigatório(s) do Cartão não foi(foram) preenchido(s)");
		}
	}
	
	public void delete(CartaoInteligentePK id) {
		findById(id);
		try {
			repository.deleteById(id);
		} catch (DataIntegrityViolationException e) {
			throw new DataIntegrityException("Não é possível deletar cartões que possuem recargas, saques ou agendamentos");
		}
	}
	
	public List<CartaoInteligente> findByCliente(Usuario cliente) {
		List<CartaoInteligente> list = repository.findByCliente(cliente);
		return list;
	}
	
}

package edu.ifes.ci.si.les.srp.controllers;

import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import edu.ifes.ci.si.les.srp.model.CartaoInteligente;
import edu.ifes.ci.si.les.srp.model.CartaoInteligentePK;
import edu.ifes.ci.si.les.srp.model.Empresa;
import edu.ifes.ci.si.les.srp.model.Usuario;
import edu.ifes.ci.si.les.srp.services.CartaoInteligenteService;
import edu.ifes.ci.si.les.srp.services.exceptions.ConstraintException;

@RestController
@RequestMapping(value = "/cartoes")
public class CartaoInteligenteController {
	
	@Autowired
	private CartaoInteligenteService service;
	
	@RequestMapping(method = RequestMethod.GET)
	public ResponseEntity<List<CartaoInteligente>> findAll() {
		List<CartaoInteligente> list = service.findAll();
		return ResponseEntity.ok().body(list);
	}
	
	@RequestMapping(value = "/{empresaId}/{numeroCartao}", method = RequestMethod.GET)
	public ResponseEntity<CartaoInteligente> find(@PathVariable Integer empresaId, @PathVariable String numeroCartao) {
		Empresa empresa = new Empresa();
		empresa.setId(empresaId);
		CartaoInteligentePK id = new CartaoInteligentePK(numeroCartao, empresa);
		CartaoInteligente obj = service.findById(id);
		return ResponseEntity.ok().body(obj);
	}
	
	@RequestMapping(method = RequestMethod.POST)
	public ResponseEntity<CartaoInteligente> insert(@Valid @RequestBody CartaoInteligente obj, BindingResult br) {
		if (br.hasErrors())
			throw new ConstraintException(br.getAllErrors().get(0).getDefaultMessage());
		obj = service.insert(obj);
		return ResponseEntity.ok().body(obj);
	}
	
	@RequestMapping(value = "/{empresaId}/{numeroCartao}", method = RequestMethod.PUT)
	public ResponseEntity<CartaoInteligente> update(@Valid @RequestBody CartaoInteligente obj, BindingResult br) {
		if (br.hasErrors())
			throw new ConstraintException(br.getAllErrors().get(0).getDefaultMessage());
		obj = service.update(obj);
		return ResponseEntity.ok().body(obj);
	}
	
	@RequestMapping(value = "/{empresaId}/{numeroCartao}", method = RequestMethod.DELETE)
	public ResponseEntity<Void> delete(@PathVariable Integer empresaId, @PathVariable String numeroCartao) {
		Empresa empresa = new Empresa();
		empresa.setId(empresaId);
		CartaoInteligentePK id = new CartaoInteligentePK(numeroCartao, empresa);
		service.delete(id);
		return ResponseEntity.noContent().build();
	}
	
	@RequestMapping(value = "/findByCliente/{idCliente}", method = RequestMethod.GET)
	public ResponseEntity<List<CartaoInteligente>> findByCliente(@PathVariable Integer idCliente) {
		Usuario cliente = new Usuario();
		cliente.setId(idCliente);
		List<CartaoInteligente> list = service.findByCliente(cliente);
		return ResponseEntity.ok().body(list);
	}
	
}

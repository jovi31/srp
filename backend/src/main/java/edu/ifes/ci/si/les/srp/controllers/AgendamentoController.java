package edu.ifes.ci.si.les.srp.controllers;

import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.annotation.Secured;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import edu.ifes.ci.si.les.srp.config.CustomUser;
import edu.ifes.ci.si.les.srp.model.Agendamento;
import edu.ifes.ci.si.les.srp.model.CartaoInteligente;
import edu.ifes.ci.si.les.srp.model.CartaoInteligentePK;
import edu.ifes.ci.si.les.srp.model.Empresa;
import edu.ifes.ci.si.les.srp.services.AgendamentoService;
import edu.ifes.ci.si.les.srp.services.exceptions.ConstraintException;
import edu.ifes.ci.si.les.srp.utils.CustomAuthorityUtils;

@RestController
@RequestMapping(value = "/agendamentos")
public class AgendamentoController {
	
	@Autowired
	private AgendamentoService service;
	
	@RequestMapping(method = RequestMethod.GET)
	@Secured("ROLE_ADMIN")
	public ResponseEntity<List<Agendamento>> findAll() {
		List<Agendamento> list = service.findAll();
		return ResponseEntity.ok().body(list);
	}
	
	@RequestMapping(value = "/{id}", method = RequestMethod.GET)
	@Secured("ROLE_ADMIN")
	public ResponseEntity<Agendamento> find(@PathVariable Integer id) {
		Agendamento obj = service.findById(id);
		return ResponseEntity.ok().body(obj);
	}
	
	@RequestMapping(method = RequestMethod.POST)
	public ResponseEntity<Agendamento> insert(@Valid @RequestBody Agendamento obj, BindingResult br,
			@AuthenticationPrincipal CustomUser userDetails) {
		if (br.hasErrors())
			throw new ConstraintException(br.getAllErrors().get(0).getDefaultMessage());
		Boolean isAdmin = CustomAuthorityUtils.isAdmin(userDetails);
		obj = service.insert(obj, userDetails.getId(), isAdmin);
		return ResponseEntity.ok().body(obj);
	}
	
	@RequestMapping(value = "/{id}", method = RequestMethod.PUT)
	public ResponseEntity<Agendamento> update(@RequestBody Agendamento obj, BindingResult br,
			@AuthenticationPrincipal CustomUser userDetails) {
		if (br.hasErrors())
			throw new ConstraintException(br.getAllErrors().get(0).getDefaultMessage());
		Boolean isAdmin = CustomAuthorityUtils.isAdmin(userDetails);
		obj = service.update(obj, userDetails.getId(), isAdmin);
		return ResponseEntity.ok().body(obj);
	}
	
	@RequestMapping(value = "/{id}", method = RequestMethod.DELETE)
	public ResponseEntity<Void> delete(@PathVariable Integer id, @AuthenticationPrincipal CustomUser userDetails) {
		Boolean isAdmin = CustomAuthorityUtils.isAdmin(userDetails);
		
		service.delete(id, userDetails.getId(), isAdmin);
		return ResponseEntity.noContent().build();
	}
	
	@RequestMapping(value = "/findByCartao/{empresa}/{numero}", method = RequestMethod.GET)
	public ResponseEntity<List<Agendamento>> findByCartao(@PathVariable Integer empresa, @PathVariable String numero,
			@AuthenticationPrincipal CustomUser userDetails) {
		Empresa empresaObj = new Empresa();
		empresaObj.setId(empresa);
		CartaoInteligentePK id = new CartaoInteligentePK(numero, empresaObj);
		CartaoInteligente ci = new CartaoInteligente();
		ci.setId(id);
		
		Boolean isAdmin = CustomAuthorityUtils.isAdmin(userDetails);
		
		List<Agendamento> list = service.findByCartaoInteligente(ci, userDetails.getId(), isAdmin);
		return ResponseEntity.ok().body(list);
	}

}

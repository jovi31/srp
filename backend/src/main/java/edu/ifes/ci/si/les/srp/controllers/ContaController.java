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
import edu.ifes.ci.si.les.srp.model.Conta;
import edu.ifes.ci.si.les.srp.model.Usuario;
import edu.ifes.ci.si.les.srp.services.ContaService;
import edu.ifes.ci.si.les.srp.services.exceptions.BusinessRuleException;
import edu.ifes.ci.si.les.srp.services.exceptions.ConstraintException;
import edu.ifes.ci.si.les.srp.utils.CustomAuthorityUtils;

@RestController
@RequestMapping(value = "/contas")
public class ContaController {
	
	@Autowired
	private ContaService service;
	
	@RequestMapping(method = RequestMethod.GET)
	@Secured("ROLE_ADMIN")
	public ResponseEntity<List<Conta>> findAll() {
		List<Conta> list = service.findAll();
		return ResponseEntity.ok().body(list);
	}
	
	@RequestMapping(value = "/{id}", method = RequestMethod.GET)
	@Secured("ROLE_ADMIN")
	public ResponseEntity<Conta> find(@PathVariable Integer id) {
		Conta obj = service.findById(id);
		return ResponseEntity.ok().body(obj);
	}
	
	@RequestMapping(method = RequestMethod.POST)
	public ResponseEntity<Conta> insert(@Valid @RequestBody Conta obj, BindingResult br,
			@AuthenticationPrincipal CustomUser userDetails) {
		if (br.hasErrors())
			throw new ConstraintException(br.getAllErrors().get(0).getDefaultMessage());
		Integer contaUserId = obj.getCliente().getId();
		Boolean isAdmin = CustomAuthorityUtils.isAdmin(userDetails);
		if (!isAdmin) {
			if (contaUserId != userDetails.getId())
				throw new BusinessRuleException("A conta deve pertencer ao usuário.");
		}
		obj = service.insert(obj);
		return ResponseEntity.ok().body(obj);
	}
	
	@RequestMapping(value = "/{id}", method = RequestMethod.PUT)
	public ResponseEntity<Conta> update(@Valid @RequestBody Conta obj, BindingResult br,
			@AuthenticationPrincipal CustomUser userDetails) {
		if (br.hasErrors())
			throw new ConstraintException(br.getAllErrors().get(0).getDefaultMessage());
		Integer contaUserId = obj.getCliente().getId();
		Boolean isAdmin = CustomAuthorityUtils.isAdmin(userDetails);
		if (!isAdmin) {
			if (contaUserId != userDetails.getId())
				throw new BusinessRuleException("A conta deve pertencer ao usuário.");
		}
		obj = service.update(obj);
		return ResponseEntity.ok().body(obj);
	}
	
	@RequestMapping(value = "/{id}", method = RequestMethod.DELETE)
	public ResponseEntity<Void> delete(@PathVariable Integer id, @AuthenticationPrincipal CustomUser userDetails) {
		Conta conta = service.findById(id);
		Integer contaUserId = conta.getCliente().getId();
		Boolean isAdmin = CustomAuthorityUtils.isAdmin(userDetails);
		if (!isAdmin) {
			if (contaUserId != userDetails.getId())
				throw new BusinessRuleException("A conta deve pertencer ao usuário.");
		}
		service.delete(id);
		return ResponseEntity.noContent().build();
	}
	
	@RequestMapping(value = "/findByCliente/{idCliente}", method = RequestMethod.GET)
	public ResponseEntity<List<Conta>> findByCliente(@PathVariable Integer idCliente,
			@AuthenticationPrincipal CustomUser userDetails) {
		Boolean isAdmin = CustomAuthorityUtils.isAdmin(userDetails);
		if (!isAdmin) {
			if (idCliente != userDetails.getId())
				throw new BusinessRuleException("Esse recurso não pode ser acessado.");
		}

		Usuario cliente = new Usuario();
		cliente.setId(idCliente);
		List<Conta> list = service.findByCliente(cliente);
		return ResponseEntity.ok().body(list);
	}

}

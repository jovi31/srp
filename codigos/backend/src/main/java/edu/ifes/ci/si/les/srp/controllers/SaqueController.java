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
import edu.ifes.ci.si.les.srp.model.Saque;
import edu.ifes.ci.si.les.srp.services.SaqueService;
import edu.ifes.ci.si.les.srp.services.exceptions.BusinessRuleException;
import edu.ifes.ci.si.les.srp.services.exceptions.ConstraintException;
import edu.ifes.ci.si.les.srp.utils.CustomAuthorityUtils;

@RestController
@RequestMapping(value = "/saques")
public class SaqueController {
	
	@Autowired
	private SaqueService service;
	
	@RequestMapping(method = RequestMethod.GET)
	@Secured("ROLE_ADMIN")
	public ResponseEntity<List<Saque>> findAll() {
		List<Saque> list = service.findAll();
		return ResponseEntity.ok().body(list);
	}
	
	@RequestMapping(value = "/{id}", method = RequestMethod.GET)
	@Secured("ROLE_ADMIN")
	public ResponseEntity<Saque> find(@PathVariable Integer id) {
		Saque saque = service.findById(id);
		return ResponseEntity.ok().body(saque);
	}
	
	@RequestMapping(method = RequestMethod.POST)
	public ResponseEntity<Saque> insert(@Valid @RequestBody Saque obj, BindingResult br,
			@AuthenticationPrincipal CustomUser userDetails) {
		if (br.hasErrors())
			throw new ConstraintException(br.getAllErrors().get(0).getDefaultMessage());
		Boolean isAdmin = CustomAuthorityUtils.isAdmin(userDetails);
		obj = service.insert(obj, userDetails.getId(), isAdmin);
		return ResponseEntity.ok().body(obj);
	}
	
	@RequestMapping(value = "/{id}", method = RequestMethod.PUT)
	@Secured("ROLE_ADMIN")
	public ResponseEntity<Saque> update(@Valid @RequestBody Saque obj, BindingResult br) {
		if (br.hasErrors())
			throw new ConstraintException(br.getAllErrors().get(0).getDefaultMessage());
		obj = service.update(obj);
		return ResponseEntity.ok().body(obj);
	}
	
	@RequestMapping(value = "/{id}", method = RequestMethod.DELETE)
	@Secured("ROLE_ADMIN")
	public ResponseEntity<Void> delete(@PathVariable Integer id) {
		service.delete(id);
		return ResponseEntity.noContent().build();
	}
	
	@RequestMapping(value = "/findByCliente/{id}", method = RequestMethod.GET)
	public ResponseEntity<List<Saque>> findByCliente(@PathVariable Integer id,
			@AuthenticationPrincipal CustomUser userDetails) {
		Boolean isAdmin = CustomAuthorityUtils.isAdmin(userDetails);
		if (!isAdmin) {
			if (id != userDetails.getId())
				throw new BusinessRuleException("Esse recurso não pode ser acessado.");
		}
		List<Saque> list = service.findByCliente(id);
		return ResponseEntity.ok().body(list);
	}
	
}

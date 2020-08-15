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
import edu.ifes.ci.si.les.srp.model.Recarga;
import edu.ifes.ci.si.les.srp.services.RecargaService;
import edu.ifes.ci.si.les.srp.services.exceptions.BusinessRuleException;
import edu.ifes.ci.si.les.srp.services.exceptions.ConstraintException;
import edu.ifes.ci.si.les.srp.utils.CustomAuthorityUtils;

@RestController
@RequestMapping(value = "/recargas")
public class RecargaController {
	
	@Autowired
	private RecargaService service;
	
	@RequestMapping(method = RequestMethod.GET)
	@Secured("ROLE_ADMIN")
	public ResponseEntity<List<Recarga>> findAll() {
		List<Recarga> list = service.findAll();
		return ResponseEntity.ok().body(list);
	}
	
	@RequestMapping(value = "/{id}", method = RequestMethod.GET)
	@Secured("ROLE_ADMIN")
	public ResponseEntity<Recarga> find(@PathVariable Integer id) {
		Recarga obj = service.findById(id);
		return ResponseEntity.ok().body(obj);
	}
	
	@RequestMapping(method = RequestMethod.POST)
	public ResponseEntity<Recarga> insert(@Valid @RequestBody Recarga obj, BindingResult br,
			@AuthenticationPrincipal CustomUser userDetails) {
		if (br.hasErrors())
			throw new ConstraintException(br.getAllErrors().get(0).getDefaultMessage());
		Boolean isAdmin = CustomAuthorityUtils.isAdmin(userDetails);
		obj = service.insert(obj, userDetails.getId(), isAdmin);
		return ResponseEntity.ok().body(obj);
	}
	
	@RequestMapping(value = "/{id}", method = RequestMethod.PUT)
	@Secured("ROLE_ADMIN")
	public ResponseEntity<Recarga> update(@Valid @RequestBody Recarga obj, BindingResult br) {
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
	public ResponseEntity<List<Recarga>> findByCliente(@PathVariable Integer id,
			@AuthenticationPrincipal CustomUser userDetails) {
		Boolean isAdmin = CustomAuthorityUtils.isAdmin(userDetails);
		if (!isAdmin) {
			if (id != userDetails.getId())
				throw new BusinessRuleException("Esse recurso não pode ser acessado.");
		}
		List<Recarga> list = service.findByCliente(id);
		return ResponseEntity.ok().body(list);
	}
	
}

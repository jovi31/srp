package edu.ifes.ci.si.les.srp.controllers;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import edu.ifes.ci.si.les.srp.model.Usuario;
import edu.ifes.ci.si.les.srp.services.UsuarioService;
import edu.ifes.ci.si.les.srp.services.exceptions.ConstraintException;

@RestController
@RequestMapping(value = "/signUp")
public class RegisterController {
	
	@Autowired
	private UsuarioService service;
	
	@RequestMapping(method = RequestMethod.POST)
	public ResponseEntity<Void> signUp(@Valid @RequestBody Usuario obj, BindingResult br) {
		if (br.hasErrors())
			throw new ConstraintException(br.getAllErrors().get(0).getDefaultMessage());
		obj.setAdmin(false);
		service.insert(obj);
		return ResponseEntity.noContent().build();
	}
	
}

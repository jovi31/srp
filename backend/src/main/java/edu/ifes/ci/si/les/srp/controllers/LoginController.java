package edu.ifes.ci.si.les.srp.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import edu.ifes.ci.si.les.srp.model.Usuario;
import edu.ifes.ci.si.les.srp.services.UsuarioService;

@RestController
@RequestMapping(value = "/signIn")
public class LoginController {
	
	@Autowired
	private UsuarioService service;
	
	@RequestMapping(method = RequestMethod.GET)
	public ResponseEntity<Usuario> signIn(@AuthenticationPrincipal UserDetails userDetails) {
		String username = userDetails.getUsername();
		Usuario usuario = service.findByEmail(username);
		return ResponseEntity.ok().body(usuario);
	}
	
}

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

import edu.ifes.ci.si.les.srp.model.UF;
import edu.ifes.ci.si.les.srp.services.UFService;
import edu.ifes.ci.si.les.srp.services.exceptions.ConstraintException;

@RestController
@RequestMapping(value = "/ufs")
public class UFController {
	
	@Autowired
	private UFService service;
	
	@RequestMapping(method=RequestMethod.GET)
	public ResponseEntity<List<UF>> findAll() {
		List<UF> list = service.findAll();
		return ResponseEntity.ok().body(list);
	}
	
	@RequestMapping(value = "/{id}", method = RequestMethod.GET)
	public ResponseEntity<UF> find(@PathVariable String id) {
		UF uf = service.findById(id);
		return ResponseEntity.ok().body(uf);
	}
	
	@RequestMapping(method = RequestMethod.POST)
	public ResponseEntity<UF> insert(@Valid @RequestBody UF obj, BindingResult br) {
		if (br.hasErrors())
			throw new ConstraintException(br.getAllErrors().get(0).getDefaultMessage());
		obj = service.insert(obj);
		return ResponseEntity.ok().body(obj);
	}
	
	@RequestMapping(value = "/{id}", method = RequestMethod.PUT)
	public ResponseEntity<UF> update(@Valid @RequestBody UF obj, BindingResult br) {
		if (br.hasErrors())
			throw new ConstraintException(br.getAllErrors().get(0).getDefaultMessage());
		obj = service.update(obj);
		return ResponseEntity.ok().body(obj);
	}
	
	@RequestMapping(value = "/{id}", method = RequestMethod.DELETE)
	public ResponseEntity<Void> delete(@PathVariable String id) {
		service.delete(id);
		return ResponseEntity.noContent().build();
	}
}

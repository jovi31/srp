package edu.ifes.ci.si.les.srp.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import edu.ifes.ci.si.les.srp.model.Conta;
import edu.ifes.ci.si.les.srp.model.Usuario;

@Repository
public interface ContaRepository extends JpaRepository<Conta, Integer> {
	
	@Transactional(readOnly = true)
	public List<Conta> findByCliente(Usuario cliente);
	
}

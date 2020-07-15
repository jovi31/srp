package edu.ifes.ci.si.les.srp.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import edu.ifes.ci.si.les.srp.model.CartaoInteligente;
import edu.ifes.ci.si.les.srp.model.CartaoInteligentePK;
import edu.ifes.ci.si.les.srp.model.Usuario;

@Repository
public interface CartaoInteligenteRepository extends JpaRepository<CartaoInteligente, CartaoInteligentePK> {
	
	@Transactional(readOnly = true)
	public List<CartaoInteligente> findByCliente(Usuario cliente);
	
}

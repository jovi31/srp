package edu.ifes.ci.si.les.srp.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import edu.ifes.ci.si.les.srp.model.Usuario;

@Repository
public interface UsuarioRepository extends JpaRepository<Usuario, Integer> {
	
	public Usuario findByEmail(String email);
	
	@Transactional(readOnly = true)
	public boolean existsByEmail(String email);
	
	@Transactional(readOnly = true)
	public boolean existsByCpf(String cpf);
	
}

package edu.ifes.ci.si.les.srp.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import edu.ifes.ci.si.les.srp.model.Saque;

@Repository
public interface SaqueRepository extends JpaRepository<Saque, Integer> {
	
	@Transactional
	@Query(value = "SELECT COUNT(*) FROM SAQUE WHERE empresa_id = ?1 AND ci_numero = ?2 AND status = 0", nativeQuery = true)
	public Integer qtdSaquesPendentes(Integer empresaId, String ciNumero);
	
	@Transactional
	@Query(value = "SELECT * FROM SAQUE WHERE data >= ?1 AND data <= ?2", nativeQuery = true)
	public List<Saque> findSaquesBetweenTimestamps(String timestamp1, String timestamp2);
	
	@Transactional
	@Query(value = "SELECT (s.*) FROM CARTAO_INTELIGENTE c, SAQUE s WHERE c.cliente_id = ?1 AND c.ci_numero = s.ci_numero AND c.empresa_id = s.empresa_id", nativeQuery = true)
	public List<Saque> findByCliente(Integer clienteId);

}

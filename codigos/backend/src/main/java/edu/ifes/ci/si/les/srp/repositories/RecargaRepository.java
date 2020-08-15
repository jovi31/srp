package edu.ifes.ci.si.les.srp.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import edu.ifes.ci.si.les.srp.model.Recarga;

@Repository
public interface RecargaRepository extends JpaRepository<Recarga, Integer> {
	
	@Transactional
	@Query(value = "SELECT (r.*) FROM CARTAO_INTELIGENTE c, RECARGA r WHERE c.cliente_id = ?1 AND c.ci_numero = r.ci_numero AND c.empresa_id = r.empresa_id", nativeQuery = true)
	public List<Recarga> findByCliente(Integer clienteId);
	
	@Transactional
	@Query(value = "SELECT COUNT(*) FROM RECARGA WHERE empresa_id = ?1 AND ci_numero = ?2 AND status = 0", nativeQuery = true)
	public Integer qtdRecargasPendentes(Integer empresaId, String ciNumero);
	
	@Transactional
	@Query(value = "SELECT SUM(valor) FROM RECARGA WHERE empresa_id = ?1 AND ci_numero = ?2 AND status = 0", nativeQuery = true)
	public Float somaRecargasPendentes(Integer empresaId, String ciNumero);
	
}

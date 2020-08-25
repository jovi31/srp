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
	
	@Transactional
	@Query(value = "SELECT e.id AS empresa_id, e.nome AS empresa, SUM(r.valor) AS total, COUNT(r.id) AS quantidade FROM RECARGA r RIGHT OUTER JOIN EMPRESA e ON r.empresa_id = e.id AND r.data >= ?1 AND r.data <= ?2 GROUP BY e.id", nativeQuery = true)
	public List<?> findTotaisAndQuantidadesRecargasOfEmpresasByPeriodo(String inicio, String termino);
	
	@Transactional
	@Query(value = "SELECT r.id, r.data, r.status, r.valor, r.empresa_id, r.ci_numero AS cartao FROM CARTAO_INTELIGENTE ci INNER JOIN RECARGA r ON ci.empresa_id = r.empresa_id AND ci.ci_numero = r.ci_numero WHERE ci.cliente_id = ?1 AND r.data >= ?2 AND r.data <= ?3", nativeQuery = true)
	public List<?> findRecargasByClienteAndPeriodo(Integer cliente, String inicio, String termino);
	
}

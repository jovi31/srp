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
	@Query(value = "SELECT * FROM SAQUE WHERE data >= CAST(?1 AS TIMESTAMP) AND data <= CAST(?2 AS TIMESTAMP)", nativeQuery = true)
	public List<Saque> findSaquesBetweenTimestamps(String timestamp1, String timestamp2);
	
	@Transactional
	@Query(value = "SELECT (s.*) FROM CARTAO_INTELIGENTE c, SAQUE s WHERE c.cliente_id = ?1 AND c.ci_numero = s.ci_numero AND c.empresa_id = s.empresa_id", nativeQuery = true)
	public List<Saque> findByCliente(Integer clienteId);
	
	@Transactional
	@Query(value = "SELECT e.id AS empresa_id, e.nome AS empresa, SUM(s.valor) AS total, COUNT(s.id) AS quantidade FROM SAQUE s RIGHT OUTER JOIN EMPRESA e ON s.empresa_id = e.id AND s.data >= CAST(?1 AS TIMESTAMP) AND s.data <= CAST(?2 AS TIMESTAMP) GROUP BY e.id", nativeQuery = true)
	public List<?> findTotaisAndQuantidadesSaquesOfEmpresasByPeriodo(String inicio, String termino);
	
	@Transactional
	@Query(value = "SELECT s.id, s.data, s.status, s.valor, s.empresa_id, s.ci_numero AS cartao, s.conta_id AS conta FROM CARTAO_INTELIGENTE ci INNER JOIN SAQUE s ON ci.empresa_id = s.empresa_id AND ci.ci_numero = s.ci_numero WHERE ci.cliente_id = ?1 AND s.data >= CAST(?2 AS TIMESTAMP) AND s.data <= CAST(?3 AS TIMESTAMP)", nativeQuery = true)
	public List<?> findSaquesByClienteAndPeriodo(Integer cliente, String inicio, String termino);

}

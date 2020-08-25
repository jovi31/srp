package edu.ifes.ci.si.les.srp.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import edu.ifes.ci.si.les.srp.model.Empresa;

@Repository
public interface EmpresaRepository extends JpaRepository<Empresa, Integer> {
	
	@Transactional
	@Query(value = "SELECT c.id AS cidade_id, c.nome AS cidade, COUNT(ec.empresa_id) AS quantidade FROM  CIDADE c  LEFT OUTER JOIN  EMPRESA_CIDADE ec ON c.id = ec.cidade_id WHERE c.uf_sigla = ?1 GROUP BY c.id", nativeQuery = true)
	public List<?> findQuantidadesEmpresasOfCidadesByUf(String siglaUf);
	
}

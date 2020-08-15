package edu.ifes.ci.si.les.srp.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import edu.ifes.ci.si.les.srp.model.Agendamento;
import edu.ifes.ci.si.les.srp.model.CartaoInteligente;

@Repository
public interface AgendamentoRepository extends JpaRepository<Agendamento, Integer> {
	
	public List<Agendamento> findByCartaoInteligente(CartaoInteligente cartaoInteligente);
	
	@Transactional
	@Query(value = "SELECT COUNT(*) FROM AGENDAMENTO WHERE empresa_id = ?1 AND ci_numero = ?2", nativeQuery = true)
	public Integer qtdAgendamentosPorCartao(Integer empresaId, String ciNumero);
	
}

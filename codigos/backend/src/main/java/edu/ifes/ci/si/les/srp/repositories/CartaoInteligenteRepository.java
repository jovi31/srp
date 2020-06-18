package edu.ifes.ci.si.les.srp.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import edu.ifes.ci.si.les.srp.model.CartaoInteligente;

@Repository
public interface CartaoInteligenteRepository extends JpaRepository<CartaoInteligente, String> {

}

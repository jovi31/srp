package edu.ifes.ci.si.les.srp.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import edu.ifes.ci.si.les.srp.model.Cidade;

@Repository
public interface CidadeRepository extends JpaRepository<Cidade, Integer> {

}

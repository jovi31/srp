package edu.ifes.ci.si.les.srp.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import edu.ifes.ci.si.les.srp.model.Empresa;

@Repository
public interface EmpresaRepository extends JpaRepository<Empresa, Integer> {

}

package com.soat.planification_entretien.infrastructure.repository;

import java.util.List;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface EntretienRepository extends CrudRepository<JpaEntretien, Integer> {
    JpaEntretien save(JpaEntretien entretien);

    List<JpaEntretien> findAll();

    JpaEntretien findByCandidatId(Integer candidatId);
}

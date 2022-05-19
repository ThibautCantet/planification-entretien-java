package com.soat.planification_entretien.infrastructure.repository;

import java.util.List;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface EntretienCrud extends CrudRepository<Entretien, Integer> {
    Entretien findByCandidat(Candidat candidat);

    Entretien save(Entretien entretien);

    List<Entretien> findAll();
}

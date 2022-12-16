package com.soat.planification_entretien.infrastructure.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface EntretienCrud extends CrudRepository<Entretien, Integer> {
    Optional<Entretien> findByCandidat_Email(String candidatEmail);

    Entretien save(Entretien entretien);

    List<Entretien> findAll();
}

package com.soat.planification_entretien.entretien.infrastructure.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface EntretienCrud extends CrudRepository<Entretien, Integer> {
    Optional<Entretien> findByCandidat_Id(Integer id);

    Entretien save(Entretien entretien);

    List<Entretien> findAll();
}

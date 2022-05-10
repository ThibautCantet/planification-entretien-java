package com.soat.planification_entretien.infrastructure.repository;

import java.util.List;
import java.util.Optional;

import com.soat.shared.infrastructure.repository.model.Candidat;
import com.soat.shared.infrastructure.repository.model.Entretien;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface JpaEntretienCrudRepository extends CrudRepository<Entretien, Integer> {
    Entretien findByCandidat(Candidat candidat);

    Optional<Entretien> findByCandidatId(Integer candidatId);

    List<Entretien> findAll();
}

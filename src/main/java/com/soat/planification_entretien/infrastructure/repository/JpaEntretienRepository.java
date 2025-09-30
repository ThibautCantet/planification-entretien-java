package com.soat.planification_entretien.infrastructure.repository;

import java.util.List;

import com.soat.planification_entretien.domain.model.Candidat;
import com.soat.planification_entretien.domain.model.Entretien;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface JpaEntretienRepository extends CrudRepository<JpaEntretien, Integer> {
    JpaEntretien findByCandidat(JpaCandidat candidat);

    Entretien save(Entretien entretien);

    List<JpaEntretien> findAll();

    JpaEntretien findByCandidatId(Integer candidatId);
}

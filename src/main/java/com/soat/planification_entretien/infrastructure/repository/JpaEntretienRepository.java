package com.soat.planification_entretien.infrastructure.repository;

import java.util.List;

import com.soat.planification_entretien.infrastructure.model.Candidat;
import com.soat.planification_entretien.infrastructure.model.Entretien;
import com.soat.planification_entretien.use_case.EntretienRepository;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface JpaEntretienRepository extends EntretienRepository, CrudRepository<Entretien, Integer> {
    Entretien findByCandidat(Candidat candidat);

    Entretien save(Entretien entretien);

    List<Entretien> findAll();
}

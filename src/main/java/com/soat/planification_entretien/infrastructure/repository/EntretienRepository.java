package com.soat.planification_entretien.infrastructure.repository;

import com.soat.planification_entretien.infrastructure.model.Candidat;
import com.soat.planification_entretien.infrastructure.model.Entretien;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface EntretienRepository extends CrudRepository<Entretien, Integer> {
    Entretien findByCandidat(Candidat candidat);

    Entretien save(Entretien entretien);
}

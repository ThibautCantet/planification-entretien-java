package com.soat.planification_entretien.repository;

import com.soat.planification_entretien.model.Candidat;
import com.soat.planification_entretien.model.Entretien;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface JpaEntretienRepository extends EntretienRepository, CrudRepository<Entretien, Integer> {

    Entretien findByCandidatId(int candidatId);

    default Entretien findByCandidat(Candidat candidat) {
        return findByCandidatId(candidat.getId());
    }

    Entretien save(Entretien entretien);
}

package com.soat.planification_entretien.use_case;

import java.util.Optional;

import com.soat.planification_entretien.domain.Candidat;
import com.soat.planification_entretien.domain.Entretien;

public interface CandidatRepository {
    Entretien findByCandidat(Candidat candidat);

    Candidat save(Candidat candidat);

    Optional<Candidat> findById(int candidatId);
}

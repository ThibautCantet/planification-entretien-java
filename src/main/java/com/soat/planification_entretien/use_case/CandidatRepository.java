package com.soat.planification_entretien.use_case;

import java.util.Optional;

import com.soat.planification_entretien.domain.Candidat;

public interface CandidatRepository {
    Candidat save(Candidat candidat);

    Optional<Candidat> findById(int candidatId);
}

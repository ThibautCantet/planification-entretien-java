package com.soat.planification_entretien.use_case;

import java.util.Optional;

import com.soat.planification_entretien.domain.model.Candidat;

public interface CandidatRepository {
    Optional<Candidat> findById(int candidatId);
}

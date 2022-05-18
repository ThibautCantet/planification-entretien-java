package com.soat.planification_entretien.domain;

import java.util.Optional;
import java.util.UUID;

public interface CandidatRepository {
    Candidat save(Candidat candidat);

    Optional<Candidat> findById(UUID candidatId);
}

package com.soat.planification_entretien.candidat.domain;

import java.util.Optional;

public interface CandidatRepository {
    Optional<Candidat> findById(int candidatId);

    Candidat save(Candidat candidat);

    Integer next();
}

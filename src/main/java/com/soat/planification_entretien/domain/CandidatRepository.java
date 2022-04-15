package com.soat.planification_entretien.domain;

import java.util.Optional;

public interface CandidatRepository {
    Candidat save(Candidat candidat);

    Optional<Candidat> findById(int candidatId);
}

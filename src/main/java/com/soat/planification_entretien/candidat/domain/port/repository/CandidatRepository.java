package com.soat.planification_entretien.candidat.domain.port.repository;

import java.util.Optional;

import com.soat.planification_entretien.candidat.domain.model.Candidat;

public interface CandidatRepository {
    Optional<Candidat> findById(int candidatId);

    Candidat save(Candidat candidat);

    Integer next();
}

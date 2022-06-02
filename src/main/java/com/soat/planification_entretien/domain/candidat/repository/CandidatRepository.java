package com.soat.planification_entretien.domain.candidat.repository;

import java.util.Optional;

import com.soat.planification_entretien.domain.candidat.entity.Candidat;


public interface CandidatRepository {
    Optional<Candidat> findById(int candidatId);

    Candidat save(Candidat candidat);
}

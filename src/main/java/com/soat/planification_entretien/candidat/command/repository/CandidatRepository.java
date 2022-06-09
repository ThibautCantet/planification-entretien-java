package com.soat.planification_entretien.candidat.command.repository;

import java.util.Optional;

import com.soat.planification_entretien.candidat.command.domain.entity.Candidat;


public interface CandidatRepository {
    Optional<Candidat> findById(int candidatId);

    Candidat save(Candidat candidat);
}

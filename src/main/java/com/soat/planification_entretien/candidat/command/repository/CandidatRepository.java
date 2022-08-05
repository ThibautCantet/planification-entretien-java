package com.soat.planification_entretien.candidat.command.repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import com.soat.planification_entretien.candidat.command.domain.entity.Candidat;


public interface CandidatRepository {
    Optional<Candidat> findById(UUID candidatId);

    List<Candidat> findAll();

    Candidat save(Candidat candidat);
}

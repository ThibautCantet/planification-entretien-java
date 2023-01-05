package com.soat.planification_entretien.domain.candidat;

import java.util.Optional;

import com.soat.planification_entretien.domain.candidat.Candidat;


public interface CandidatRepository {
    Optional<Candidat> findById(int candidatId);

    Candidat save(Candidat candidat);
}

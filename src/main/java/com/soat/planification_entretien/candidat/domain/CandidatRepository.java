package com.soat.planification_entretien.candidat.domain;

import java.util.Optional;


public interface CandidatRepository {
    Optional<CandidatProspect> findById(int candidatId);

    CandidatProspect save(CandidatProspect candidat);
}

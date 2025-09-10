package com.soat.planification_entretien.domain.preparation;

import java.util.Optional;


public interface CandidatRepository {
    Optional<Candidat> findById(int candidatId);

    Candidat save(Candidat candidat);
}

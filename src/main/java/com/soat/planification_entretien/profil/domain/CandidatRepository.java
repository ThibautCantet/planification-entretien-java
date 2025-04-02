package com.soat.planification_entretien.profil.domain;

import java.util.Optional;


public interface CandidatRepository {
    Optional<Candidat> findById(int candidatId);

    Candidat save(Candidat candidat);
}

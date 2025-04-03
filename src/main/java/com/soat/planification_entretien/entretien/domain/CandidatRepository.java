package com.soat.planification_entretien.entretien.domain;

import java.util.Optional;

public interface CandidatRepository {
    Optional<Candidat> findById(int id);
}

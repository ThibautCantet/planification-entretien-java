package com.soat.planification_entretien.entretien.domain;

import java.util.Optional;
import java.util.UUID;

public interface CandidatRepository {
    Optional<Candidat> findById(UUID id);
}

package com.soat.planification_entretien.domain;

import java.util.UUID;

public interface CandidatRepository {
    Candidat findById(UUID candidatId);
}

package com.soat.planification_entretien.domain;

import com.soat.candidat.domain.Candidat;

import java.util.UUID;

public interface CandidatRepository {
    Candidat findById(UUID candidatId);
}

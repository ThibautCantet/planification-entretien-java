package com.soat.planification_entretien.infrastructure;

import com.soat.candidat.domain.Candidat;
import com.soat.planification_entretien.domain.CandidatRepository;

import java.util.UUID;

public class InMemoryCandidatRepository implements CandidatRepository {
    private final com.soat.candidat.infrastructure.InMemoryCandidatRepository candidatRepository;

    public InMemoryCandidatRepository(com.soat.candidat.infrastructure.InMemoryCandidatRepository candidatRepository) {
        this.candidatRepository = candidatRepository;
    }

    @Override
    public Candidat findById(UUID candidatId) {
        return candidatRepository.findById(candidatId);
    }
}

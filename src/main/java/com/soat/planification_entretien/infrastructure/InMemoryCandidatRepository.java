package com.soat.planification_entretien.infrastructure;

import com.soat.planification_entretien.domain.Candidat;
import com.soat.planification_entretien.domain.CandidatRepository;

import java.util.UUID;

import static java.util.Optional.ofNullable;

public class InMemoryCandidatRepository implements CandidatRepository {
    private final com.soat.candidat.infrastructure.InMemoryCandidatRepository candidatRepository;

    public InMemoryCandidatRepository(com.soat.candidat.infrastructure.InMemoryCandidatRepository candidatRepository) {
        this.candidatRepository = candidatRepository;
    }

    @Override
    public Candidat findById(UUID candidatId) {
        return ofNullable(candidatRepository.findById(candidatId))
                .map(candidat -> new Candidat(candidatId, candidat.getLanguage(), candidat.getEmail(), candidat.getExperienceEnAnnees()))
                .orElse(null);
    }
}

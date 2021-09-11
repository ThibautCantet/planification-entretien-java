package com.soat.planification_entretien.infrastructure;

import com.soat.planification_entretien.domain.RecruteurRepository;
import com.soat.planification_entretien.domain.Recruteur;

import java.util.UUID;

import static java.util.Optional.ofNullable;

public class InMemoryRecruteurRepository implements RecruteurRepository {
    private final com.soat.recruteur.infrastructure.InMemoryRecruteurRepository recruteurRepository;

    public InMemoryRecruteurRepository(com.soat.recruteur.infrastructure.InMemoryRecruteurRepository recruteurRepository) {
        this.recruteurRepository = recruteurRepository;
    }

    @Override
    public Recruteur findById(UUID recruteurId) {
        return ofNullable(recruteurRepository.findById(recruteurId))
                .map(recruteur -> new Recruteur(recruteur.getEmail(), recruteur.getExperienceEnAnnees()))
                .orElse(null);
    }
}

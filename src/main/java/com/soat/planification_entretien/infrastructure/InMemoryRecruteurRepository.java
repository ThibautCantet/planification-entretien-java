package com.soat.planification_entretien.infrastructure;

import com.soat.planification_entretien.domain.RecruteurRepository;
import com.soat.recruteur.domain.Recruteur;

import java.util.UUID;

public class InMemoryRecruteurRepository implements RecruteurRepository {
    private final com.soat.recruteur.infrastructure.InMemoryRecruteurRepository recruteurRepository;

    public InMemoryRecruteurRepository(com.soat.recruteur.infrastructure.InMemoryRecruteurRepository recruteurRepository) {
        this.recruteurRepository = recruteurRepository;
    }

    @Override
    public Recruteur findById(UUID recruteurId) {
        return recruteurRepository.findById(recruteurId);
    }
}

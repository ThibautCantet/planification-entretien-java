package com.soat.planification_entretien.entretien.command.infrastructure.repository;

import java.util.HashMap;
import java.util.Map;

import com.soat.planification_entretien.entretien.command.domain.entity.Entretien;
import com.soat.planification_entretien.entretien.command.domain.entity.Candidat;
import com.soat.planification_entretien.entretien.command.domain.repository.EntretienRepository;

//@Repository
public class InMemoryEntretienRepository implements EntretienRepository {

    private final Map<Integer, Entretien> cache = new HashMap<>();

    @Override
    public int save(Entretien entretien) {
        Integer newId = cache.size() + 1;
        entretien = Entretien.of(newId, entretien);
        cache.put(entretien.getId(), entretien);
        return newId;
    }

    public Entretien findByCandidat(Candidat candidat) {
        return cache.values().stream()
                .filter(entretien -> entretien.getCandidat().getId().equals(candidat.getId()))
                .findFirst()
                .orElse(null);
    }

    @Override
    public Entretien findById(int id) {
        return null;
    }
}
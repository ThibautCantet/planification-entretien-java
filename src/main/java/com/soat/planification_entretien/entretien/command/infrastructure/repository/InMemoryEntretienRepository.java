package com.soat.planification_entretien.entretien.command.infrastructure.repository;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

import com.soat.planification_entretien.entretien.command.domain.entity.Entretien;
import com.soat.planification_entretien.entretien.command.domain.entity.Candidat;
import com.soat.planification_entretien.entretien.command.domain.repository.EntretienRepository;

//@Repository
public class InMemoryEntretienRepository implements EntretienRepository {

    private final Map<String, Entretien> cache = new HashMap<>();

    @Override
    public UUID next() {
        return UUID.randomUUID();
    }

    @Override
    public String save(Entretien entretien) {
        Integer newId = cache.size() + 1;
        entretien = Entretien.of(newId.toString(), entretien);
        cache.put(entretien.getId(), entretien);
        return newId.toString();
    }

    public Entretien findByCandidat(Candidat candidat) {
        return cache.values().stream()
                .filter(entretien -> entretien.getCandidat().getId().equals(candidat.getId()))
                .findFirst()
                .orElse(null);
    }

    @Override
    public Entretien findById(String id) {
        return null;
    }
}

package com.soat.planification_entretien.infrastructure.repository;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.soat.planification_entretien.domain.Candidat;
import com.soat.planification_entretien.domain.Entretien;
import com.soat.planification_entretien.domain.EntretienRepository;

//@Repository
public class InMemoryEntretienRepository implements EntretienRepository {

    private final Map<Integer, Entretien> cache = new HashMap<>();

    @Override
    public void save(Entretien entretien) {
        Integer newId = cache.size() + 1;
        entretien = Entretien.of(newId, entretien);
        cache.put(entretien.getId(), entretien);
    }

    @Override
    public List<Entretien> findAll() {
        return cache.values().stream().toList();
    }

    public Entretien findByCandidat(Candidat candidat) {
        return cache.values().stream()
                .filter(entretien -> entretien.getCandidat().getId().equals(candidat.getId()))
                .findFirst()
                .orElse(null);
    }
}

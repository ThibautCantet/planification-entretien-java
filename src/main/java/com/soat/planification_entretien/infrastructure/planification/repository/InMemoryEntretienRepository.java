package com.soat.planification_entretien.infrastructure.planification.repository;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.soat.planification_entretien.domain.planification.CandidatSuivi;
import com.soat.planification_entretien.domain.planification.Entretien;
import com.soat.planification_entretien.domain.planification.EntretienRepository;

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

    public Entretien findByCandidat(CandidatSuivi candidat) {
        return cache.values().stream()
                .filter(entretien -> entretien.getCandidat().id().equals(candidat.id()))
                .findFirst()
                .orElse(null);
    }

    public Entretien findById(int id) {
        return cache.get(id);
    }
}

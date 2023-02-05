package com.soat.planification_entretien.entretien.infrastructure.repository;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import com.soat.planification_entretien.entretien.command.domain.Entretien;
import com.soat.planification_entretien.entretien.command.domain.EntretienRepository;

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

    @Override
    public Entretien findByCandidatId(int candidatId) {
        return cache.values().stream()
                .filter(entretien -> entretien.getCandidat().id().equals(candidatId))
                .findFirst()
                .orElse(null);
    }

    @Override
    public Optional<Entretien> findById(int entretienId) {
        return Optional.ofNullable(cache.get(entretienId));
    }
}

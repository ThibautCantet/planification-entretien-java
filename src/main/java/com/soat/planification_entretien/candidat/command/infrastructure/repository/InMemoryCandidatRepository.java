package com.soat.planification_entretien.candidat.command.infrastructure.repository;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.UUID;

import com.soat.planification_entretien.candidat.command.domain.entity.Candidat;
import com.soat.planification_entretien.candidat.command.repository.CandidatRepository;

//@Repository
public class InMemoryCandidatRepository implements CandidatRepository {
    private final Map<UUID, Candidat> cache = new HashMap<>();

    @Override
    public Optional<Candidat> findById(UUID candidatId) {
        return Optional.ofNullable(cache.get(candidatId));
    }

    @Override
    public List<Candidat> findAll() {
        return cache.values().stream().toList();
    }

    @Override
    public Candidat save(Candidat candidat) {
        cache.put(candidat.getId(), candidat);
        return candidat;
    }
}

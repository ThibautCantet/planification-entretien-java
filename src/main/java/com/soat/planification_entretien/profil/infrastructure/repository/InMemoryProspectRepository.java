package com.soat.planification_entretien.profil.infrastructure.repository;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.UUID;

import com.soat.planification_entretien.profil.domain.Prospect;
import com.soat.planification_entretien.profil.domain.ProspectRepository;

//@Repository
public class InMemoryProspectRepository implements ProspectRepository {
    private final Map<UUID, Prospect> cache = new HashMap<>();

    @Override
    public UUID next() {
        return UUID.randomUUID();
    }

    @Override
    public Optional<Prospect> findById(UUID candidatId) {
        return Optional.ofNullable(cache.get(candidatId));
    }

    @Override
    public Prospect save(Prospect prospect) {
        cache.put(prospect.getId(), prospect);
        return prospect;
    }

    @Override
    public List<Prospect> findAll() {
        return List.of();
    }
}

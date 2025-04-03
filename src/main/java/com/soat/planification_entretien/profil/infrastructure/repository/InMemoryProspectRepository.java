package com.soat.planification_entretien.profil.infrastructure.repository;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

import com.soat.planification_entretien.profil.domain.Prospect;
import com.soat.planification_entretien.profil.domain.ProspectRepository;

//@Repository
public class InMemoryProspectRepository implements ProspectRepository {
    private final Map<Integer, Prospect> cache = new HashMap<>();

    @Override
    public Optional<Prospect> findById(int candidatId) {
        return Optional.ofNullable(cache.get(candidatId));
    }

    @Override
    public Prospect save(Prospect prospect) {
        Integer newId = cache.size() + 1;
        prospect = Prospect.of(newId, prospect);
        cache.put(newId, prospect);
        return prospect;
    }
}

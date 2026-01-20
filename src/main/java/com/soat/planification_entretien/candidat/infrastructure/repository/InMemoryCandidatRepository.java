package com.soat.planification_entretien.candidat.infrastructure.repository;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

import com.soat.planification_entretien.candidat.domain.CandidatProspect;
import com.soat.planification_entretien.candidat.domain.CandidatRepository;

//@Repository
public class InMemoryCandidatRepository implements CandidatRepository {
    private final Map<Integer, CandidatProspect> cache = new HashMap<>();

    @Override
    public Optional<CandidatProspect> findById(int candidatId) {
        return Optional.ofNullable(cache.get(candidatId));
    }

    @Override
    public CandidatProspect save(CandidatProspect candidat) {
        Integer newId = cache.size() + 1;
        candidat = CandidatProspect.of(newId, candidat);
        cache.put(newId, candidat);
        return candidat;
    }
}

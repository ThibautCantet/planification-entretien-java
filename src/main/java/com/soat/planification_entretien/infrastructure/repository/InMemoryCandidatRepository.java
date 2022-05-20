package com.soat.planification_entretien.infrastructure.repository;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

import com.soat.planification_entretien.domain.candidat.Candidat;
import com.soat.planification_entretien.domain.candidat.CandidatRepository;
import org.springframework.stereotype.Repository;

@Repository
public class InMemoryCandidatRepository implements CandidatRepository {
    private final Map<Integer, Candidat> cache = new HashMap<>();

    @Override
    public Optional<Candidat> findById(int candidatId) {
        return Optional.ofNullable(cache.get(candidatId));
    }

    @Override
    public Candidat save(Candidat candidat) {
        Integer newId = cache.size() + 1;
        candidat = Candidat.of(newId, candidat);
        cache.put(newId, candidat);
        return candidat;
    }
}

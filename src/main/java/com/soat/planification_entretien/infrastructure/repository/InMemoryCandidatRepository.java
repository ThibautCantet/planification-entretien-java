package com.soat.planification_entretien.infrastructure.repository;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

import com.soat.planification_entretien.domain.Candidat;
import com.soat.planification_entretien.domain.CandidatRepository;
import org.springframework.stereotype.Repository;

@Repository
public class InMemoryCandidatRepository implements CandidatRepository {
    private final Map<Integer, Candidat> map = new HashMap<>();

    @Override
    public Candidat save(Candidat candidat) {
        int id = map.size() + 1;
        Candidat toSave = Candidat.of(id, candidat.getLanguage(), candidat.getEmail(), candidat.getExperienceInYears());
        map.put(id, toSave);
        return toSave;
    }

    @Override
    public Optional<Candidat> findById(int candidatId) {
        return Optional.ofNullable(map.get(candidatId));
    }
}

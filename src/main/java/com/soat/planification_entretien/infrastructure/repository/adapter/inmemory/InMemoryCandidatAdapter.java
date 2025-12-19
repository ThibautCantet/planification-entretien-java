package com.soat.planification_entretien.infrastructure.repository.adapter.inmemory;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

import com.soat.planification_entretien.domain.Candidat;
import com.soat.planification_entretien.domain.CandidatPort;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Repository;

@Repository
@Primary
public class InMemoryCandidatAdapter implements CandidatPort {

    private final Map<Integer, Candidat> candidats = new HashMap<>();

    @Override
    public Optional<Candidat> findById(int candidatId) {
        return Optional.ofNullable(candidats.getOrDefault(candidatId, null));
    }

    @Override
    public Candidat save(Candidat candidat) {
        var newId = candidats.size() + 1;
        var candidatWithId = new Candidat(newId, candidat.getLanguage(), candidat.getEmail(), candidat.getExperienceInYears());
        candidats.put(newId, candidatWithId);
        return candidatWithId;
    }
}

package com.soat.planification_entretien.infrastructure.repository.adapter;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

import com.soat.planification_entretien.application.use_case.output_port.CandidatPort;
import com.soat.planification_entretien.domain.model.Candidat;
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
    public int save(Candidat candidat) {
        var newId = candidats.size() + 1;
        var candidatWithId = new Candidat(newId, candidat.getLanguage(), candidat.getEmail(), candidat.getExperienceInYears());
        candidats.put(newId, candidatWithId);
        return newId;
    }
}

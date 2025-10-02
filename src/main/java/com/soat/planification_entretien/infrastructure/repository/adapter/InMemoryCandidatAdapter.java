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
    private final Map<Integer, Candidat> map = new HashMap<>();

    @Override
    public int save(Candidat candidat) {
        int id = map.size() + 1;
        Candidat toSave = new Candidat(id, candidat.getLanguage(), candidat.getEmail(), candidat.getExperienceInYears());
        map.put(id, toSave);
        return id;
    }

    @Override
    public Optional<Candidat> findById(int candidatId) {
        return Optional.ofNullable(map.get(candidatId));
    }
}

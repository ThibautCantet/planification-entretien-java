package com.soat.planification_entretien.infrastructure.repository.adapter.jpa;

import java.util.Optional;

import com.soat.planification_entretien.domain.Candidat;
import com.soat.planification_entretien.domain.CandidatPort;
import com.soat.planification_entretien.infrastructure.repository.CandidatRepository;
import com.soat.planification_entretien.infrastructure.repository.JpaCandidat;
import org.springframework.stereotype.Repository;

//@Repository
public class CandidatAdapter implements CandidatPort {
    private final CandidatRepository candidatRepository;

    public CandidatAdapter(CandidatRepository candidatRepository) {
        this.candidatRepository = candidatRepository;
    }

    @Override
    public Optional<Candidat> findById(int candidatId) {
        return candidatRepository.findById(candidatId)
                .map(c -> new Candidat(c.getId(), c.getLanguage(), c.getEmail(), c.getExperienceInYears()));
    }

    @Override
    public Candidat save(Candidat candidat) {
        var toSave = new JpaCandidat(candidat.getLanguage(), candidat.getEmail(), candidat.getExperienceInYears());
        var saved = candidatRepository.save(toSave);
        return new Candidat(saved.getId(), saved.getLanguage(), saved.getEmail(), saved.getExperienceInYears());
    }
}

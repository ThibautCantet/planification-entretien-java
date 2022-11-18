package com.soat.planification_entretien.archi_hexa.infrastructure.jpa.adapter;

import com.soat.planification_entretien.archi_hexa.domain.entity.Candidat;
import com.soat.planification_entretien.archi_hexa.domain.port.CandidatPort;
import com.soat.planification_entretien.archi_hexa.infrastructure.jpa.model.JpaCandidat;
import com.soat.planification_entretien.archi_hexa.infrastructure.jpa.repository.CandidatRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public class JpaCandidatAdapter implements CandidatPort {
    private final CandidatRepository candidatRepository;

    public JpaCandidatAdapter(CandidatRepository candidatRepository) {
        this.candidatRepository = candidatRepository;
    }

    @Override
    public Optional<Candidat> findById(Integer candidatId) {
        final Optional<JpaCandidat> optionalJpaCandidat = candidatRepository.findById(candidatId);
        return optionalJpaCandidat.map(JpaCandidatAdapter::toCandidat);
    }

    @Override
    public Integer save(Candidat candidat) {
        final JpaCandidat jpaCandidat = new JpaCandidat(candidat.language(), candidat.email(), candidat.experienceInYears());
        final JpaCandidat savedCandidat = candidatRepository.save(jpaCandidat);
        return savedCandidat.getId();
    }

    private static Candidat toCandidat(JpaCandidat jpaCandidat) {
        return new Candidat(jpaCandidat.getId(), jpaCandidat.getLanguage(), jpaCandidat.getEmail(), jpaCandidat.getExperienceInYears());
    }
}

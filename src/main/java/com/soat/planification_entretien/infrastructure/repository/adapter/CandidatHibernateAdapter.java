package com.soat.planification_entretien.infrastructure.repository.adapter;

import java.util.Optional;

import com.soat.planification_entretien.application.use_case.output_port.CandidatPort;
import com.soat.planification_entretien.domain.model.Candidat;
import com.soat.planification_entretien.infrastructure.repository.JpaCandidat;
import com.soat.planification_entretien.infrastructure.repository.JpaCandidatRepository;
import org.springframework.stereotype.Repository;

//@Repository
public class CandidatHibernateAdapter implements CandidatPort {

    private final JpaCandidatRepository jpaCandidatRepository;

    public CandidatHibernateAdapter(JpaCandidatRepository jpaCandidatRepository) {
        this.jpaCandidatRepository = jpaCandidatRepository;
    }

    @Override
    public Optional<Candidat> findById(int candidatId) {
        Optional<JpaCandidat> jpaCandidat = jpaCandidatRepository.findById(candidatId);
        return jpaCandidat.map(JpaCandidat::toDomain);
    }

    @Override
    public int save(Candidat candidat) {
        var jpaCandidat = JpaCandidat.fromDomain(candidat);
        JpaCandidat saved = jpaCandidatRepository.save(jpaCandidat);
        return saved.getId();
    }
}

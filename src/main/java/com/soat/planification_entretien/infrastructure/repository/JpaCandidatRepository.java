package com.soat.planification_entretien.infrastructure.repository;

import java.util.Optional;

import com.soat.planification_entretien.domain.Candidat;
import com.soat.planification_entretien.use_case.CandidatRepository;
import org.springframework.stereotype.Repository;

@Repository
public class JpaCandidatRepository implements CandidatRepository {
    private final JpaCandidatCrudRepository jpaCandidatCrudRepository;

    public JpaCandidatRepository(JpaCandidatCrudRepository jpaCandidatCrudRepository) {
        this.jpaCandidatCrudRepository = jpaCandidatCrudRepository;
    }

    @Override
    public Candidat save(Candidat candidat) {
        var jpaCandidat = new com.soat.planification_entretien.infrastructure.model.Candidat(candidat.getLanguage(), candidat.getEmail(), candidat.getExperienceInYears());
        var saved = jpaCandidatCrudRepository.save(jpaCandidat);
        return Candidat.of(saved.getId(), candidat);
    }

    @Override
    public Optional<Candidat> findById(int candidatId) {
        var candidat = jpaCandidatCrudRepository.findById(candidatId);
        return candidat.map(r -> Candidat.of(candidatId, r.getLanguage(), r.getEmail(), r.getExperienceInYears()));
    }
}

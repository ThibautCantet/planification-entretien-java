package com.soat.planification_entretien.infrastructure.repository;

import java.util.Optional;
import java.util.UUID;

import com.soat.planification_entretien.domain.Candidat;
import com.soat.planification_entretien.domain.CandidatRepository;
import com.soat.shared.infrastructure.repository.JpaCandidatCrudRepository;
import org.springframework.stereotype.Repository;

@Repository("entretien")
public class JpaCandidatRepository implements CandidatRepository {
    private final JpaCandidatCrudRepository jpaCandidatCrudRepository;

    public JpaCandidatRepository(JpaCandidatCrudRepository jpaCandidatCrudRepository) {
        this.jpaCandidatCrudRepository = jpaCandidatCrudRepository;
    }

    @Override
    public Candidat save(Candidat candidat) {
        var jpaCandidat = new com.soat.shared.infrastructure.repository.model.Candidat(candidat.getId(), candidat.getLanguage(), candidat.getEmail(), candidat.getExperienceInYears());
        jpaCandidatCrudRepository.save(jpaCandidat);
        return candidat;
    }

    @Override
    public Optional<Candidat> findById(UUID candidatId) {
        var candidat = jpaCandidatCrudRepository.findById(candidatId);
        return candidat.map(r -> Candidat.of(candidatId, r.getLanguage(), r.getEmail(), r.getExperienceInYears()));
    }
}

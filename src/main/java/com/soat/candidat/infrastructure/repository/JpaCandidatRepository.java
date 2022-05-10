package com.soat.candidat.infrastructure.repository;

import com.soat.candidat.domain.Candidat;
import com.soat.candidat.domain.CandidatRepository;
import com.soat.shared.infrastructure.repository.JpaCandidatCrudRepository;
import org.springframework.stereotype.Repository;

@Repository("candidat")
public class JpaCandidatRepository implements CandidatRepository {
    private final JpaCandidatCrudRepository jpaCandidatCrudRepository;

    public JpaCandidatRepository(JpaCandidatCrudRepository jpaCandidatCrudRepository) {
        this.jpaCandidatCrudRepository = jpaCandidatCrudRepository;
    }

    @Override
    public Candidat save(Candidat candidat) {
        var jpaCandidat = new com.soat.shared.infrastructure.repository.model.Candidat(candidat.getLanguage(), candidat.getEmail(), candidat.getExperienceInYears());
        var saved = jpaCandidatCrudRepository.save(jpaCandidat);
        return Candidat.of(saved.getId(), candidat);
    }
}

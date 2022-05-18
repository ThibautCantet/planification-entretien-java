package com.soat.candidat.infrastructure.repository;

import java.util.UUID;

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
    public UUID next() {
        return UUID.randomUUID();
    }

    @Override
    public Candidat save(Candidat candidat) {
        var jpaCandidat = new com.soat.shared.infrastructure.repository.model.Candidat(candidat.getId(), candidat.getLanguage(), candidat.getEmail(), candidat.getExperienceEnAnnees());
        var s = jpaCandidatCrudRepository.save(jpaCandidat);
        return candidat;
    }
}

package com.soat.planification_entretien.infrastructure.repository;

import java.util.Optional;

import com.soat.planification_entretien.domain.Candidat;
import com.soat.planification_entretien.domain.CandidatPort;
import org.springframework.stereotype.Repository;

@Repository
public class CandidatAdapter implements CandidatPort {
    private final CandidatRepository candidatRepository;

    public CandidatAdapter(CandidatRepository candidatRepository) {
        this.candidatRepository = candidatRepository;
    }

    @Override
    public Optional<Candidat> findById(int candidatId) {
        return candidatRepository.findById(candidatId);
    }
}

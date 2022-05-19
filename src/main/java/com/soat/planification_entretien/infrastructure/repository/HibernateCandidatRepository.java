package com.soat.planification_entretien.infrastructure.repository;

import java.util.Optional;

import com.soat.planification_entretien.use_case.CandidatRepository;
import org.springframework.stereotype.Repository;

@Repository
public class HibernateCandidatRepository implements CandidatRepository {
    private final CandidatCrud candidatCrud;

    public HibernateCandidatRepository(CandidatCrud candidatCrud) {
        this.candidatCrud = candidatCrud;
    }

    @Override
    public Optional<com.soat.planification_entretien.domain.model.Candidat> findById(int candidatId) {
        return candidatCrud.findById(candidatId).map(
                candidat -> new com.soat.planification_entretien.domain.model.Candidat(
                        candidatId,
                        candidat.getLanguage(),
                        candidat.getEmail(),
                        candidat.getExperienceInYears()
                )
        );
    }
}

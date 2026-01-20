package com.soat.planification_entretien.candidat.infrastructure.repository;

import java.util.Optional;

import com.soat.planification_entretien.candidat.domain.CandidatProspect;
import com.soat.planification_entretien.candidat.domain.CandidatRepository;
import org.springframework.stereotype.Repository;

@Repository
public class HibernateCandidatRepository implements CandidatRepository {
    private final CandidatCrud candidatCrud;

    public HibernateCandidatRepository(CandidatCrud candidatCrud) {
        this.candidatCrud = candidatCrud;
    }

    @Override
    public Optional<CandidatProspect> findById(int candidatId) {
        return candidatCrud.findById(candidatId).map(
                candidat -> new CandidatProspect(
                        candidatId,
                        candidat.getLanguage(),
                        candidat.getEmail(),
                        candidat.getExperienceInYears()
                )
        );
    }

    @Override
    public CandidatProspect save(CandidatProspect candidat) {
        var toSave = new com.soat.planification_entretien.candidat.infrastructure.repository.Candidat(candidat.getLanguage(), candidat.getEmail(), candidat.getExperienceInYears());
        var saved = candidatCrud.save(toSave);
        return CandidatProspect.of(saved.getId(), candidat);
    }
}

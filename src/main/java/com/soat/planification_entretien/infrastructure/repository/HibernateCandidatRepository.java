package com.soat.planification_entretien.infrastructure.repository;

import java.util.Optional;

import com.soat.planification_entretien.domain.Candidat;
import com.soat.planification_entretien.domain.CandidatRepository;
import org.springframework.stereotype.Repository;

@Repository
public class HibernateCandidatRepository implements CandidatRepository {
    private final CandidatCrud candidatCrud;

    public HibernateCandidatRepository(CandidatCrud candidatCrud) {
        this.candidatCrud = candidatCrud;
    }

    @Override
    public Optional<Candidat> findById(int candidatId) {
        return candidatCrud.findById(candidatId).map(
                candidat -> new Candidat(
                        candidatId,
                        candidat.getLanguage(),
                        candidat.getEmail(),
                        candidat.getExperienceInYears()
                )
        );
    }

    @Override
    public Candidat save(Candidat candidat) {
        var toSave = new com.soat.planification_entretien.infrastructure.repository.Candidat(candidat.getLanguage(), candidat.getEmail(), candidat.getExperienceInYears());
        var saved = candidatCrud.save(toSave);
        return Candidat.of(saved.getId(), candidat);
    }
}

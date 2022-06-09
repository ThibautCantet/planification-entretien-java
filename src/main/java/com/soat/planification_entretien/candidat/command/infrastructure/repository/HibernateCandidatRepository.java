package com.soat.planification_entretien.candidat.command.infrastructure.repository;

import java.util.Optional;

import com.soat.planification_entretien.candidat.command.domain.entity.Candidat;
import com.soat.planification_entretien.candidat.command.repository.CandidatRepository;
import com.soat.planification_entretien.infrastructure.repository.CandidatCrud;
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
        com.soat.planification_entretien.infrastructure.repository.Candidat toSave = new com.soat.planification_entretien.infrastructure.repository.Candidat(candidat.getLanguage(), candidat.getEmail(), candidat.getExperienceInYears());
        com.soat.planification_entretien.infrastructure.repository.Candidat saved = candidatCrud.save(toSave);
        return Candidat.of(saved.getId(), candidat);
    }
}

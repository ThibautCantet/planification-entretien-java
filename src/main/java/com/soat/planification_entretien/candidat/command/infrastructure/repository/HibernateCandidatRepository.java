package com.soat.planification_entretien.candidat.command.infrastructure.repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

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
    public Optional<Candidat> findById(UUID candidatId) {
        return candidatCrud.findById(candidatId)
                .map(candidat -> new Candidat(
                                candidatId,
                                candidat.getLanguage(),
                                candidat.getEmail(),
                                candidat.getExperienceInYears()
                        )
                );
    }

    @Override
    public List<Candidat> findAll() {
        return candidatCrud.findAll()
                .stream().map(candidat -> new Candidat(
                                candidat.getId(),
                                candidat.getLanguage(),
                                candidat.getEmail(),
                                candidat.getExperienceInYears()
                        )
                )
                .toList();
    }

    @Override
    public Candidat save(Candidat candidat) {
        var toSave = new com.soat.planification_entretien.infrastructure.repository.Candidat(candidat.getId(), candidat.getLanguage(), candidat.getEmail(), candidat.getExperienceInYears());
        candidatCrud.save(toSave);
        return candidat;
    }
}

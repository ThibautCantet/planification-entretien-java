package com.soat.planification_entretien.candidat.infrastructure.repository;

import java.util.Optional;

import com.soat.planification_entretien.candidat.domain.Candidat;
import com.soat.planification_entretien.candidat.domain.CandidatRepository;
import com.soat.planification_entretien.recruteur.infrastructure.repository.RecruteurCrud;
import org.springframework.stereotype.Repository;

@Repository
public class HibernateCandidatRepository implements CandidatRepository {
    private final CandidatCrud candidatCrud;
    private final RecruteurCrud recruteurCrud;

    public HibernateCandidatRepository(CandidatCrud candidatCrud,
                                       RecruteurCrud recruteurCrud) {
        this.candidatCrud = candidatCrud;
        this.recruteurCrud = recruteurCrud;
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
        var toSave = new com.soat.planification_entretien.candidat.infrastructure.repository.Candidat(
                candidat.getId(),
                candidat.getLanguage(),
                candidat.getAdresseEmail(),
                candidat.getExperienceInYears());
        var saved = candidatCrud.save(toSave);
        return Candidat.of(saved.getId(), candidat);
    }

    @Override
    public Integer next() {
        return (int) recruteurCrud.count() + 1;
    }
}

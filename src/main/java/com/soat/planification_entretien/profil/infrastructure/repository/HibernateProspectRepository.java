package com.soat.planification_entretien.profil.infrastructure.repository;

import java.util.Optional;

import com.soat.planification_entretien.profil.domain.Prospect;
import com.soat.planification_entretien.profil.domain.ProspectRepository;
import org.springframework.stereotype.Repository;

@Repository
public class HibernateProspectRepository implements ProspectRepository {
    private final CandidatCrud candidatCrud;

    public HibernateProspectRepository(CandidatCrud candidatCrud) {
        this.candidatCrud = candidatCrud;
    }

    @Override
    public Optional<Prospect> findById(int candidatId) {
        return candidatCrud.findById(candidatId).map(
                candidat -> new Prospect(
                        candidatId,
                        candidat.getLanguage(),
                        candidat.getEmail(),
                        candidat.getExperienceInYears()
                )
        );
    }

    @Override
    public Prospect save(Prospect prospect) {
        var toSave = new com.soat.planification_entretien.profil.infrastructure.repository.Candidat(prospect.getLanguage(), prospect.getEmail(), prospect.getExperienceInYears());
        var saved = candidatCrud.save(toSave);
        return Prospect.of(saved.getId(), prospect);
    }
}

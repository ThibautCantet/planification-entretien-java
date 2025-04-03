package com.soat.planification_entretien.profil.infrastructure.repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

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
    public Optional<Prospect> findById(UUID candidatId) {
        return candidatCrud.findByUuid(candidatId).map(
                candidat -> new Prospect(
                        candidat.getId(),
                        candidat.getUuid(),
                        candidat.getLanguage(),
                        candidat.getEmail(),
                        candidat.getExperienceInYears()
                )
        );
    }

    @Override
    public Prospect save(Prospect prospect) {
        var toSave = new com.soat.planification_entretien.profil.infrastructure.repository.Candidat(
                prospect.getId(),
                prospect.getLanguage(),
                prospect.getEmail(),
                prospect.getExperienceInYears());
        var saved = candidatCrud.save(toSave);
        return Prospect.of(saved.getId(), prospect);
    }

    @Override
    public List<Prospect> findAll() {
        return candidatCrud.findAll()
                .stream()
                .map(c -> new Prospect(
                        c.getId(),
                        c.getUuid(),
                        c.getLanguage(),
                        c.getEmail(),
                        c.getExperienceInYears()
                ))
                .toList();
    }
}

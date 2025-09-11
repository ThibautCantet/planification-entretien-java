package com.soat.planification_entretien.infrastructure.preparation.repository;

import java.util.List;

import com.soat.planification_entretien.domain.Profil;
import com.soat.planification_entretien.domain.planification.CandidatSuivi;
import com.soat.planification_entretien.domain.planification.EntretienRepository;
import com.soat.planification_entretien.domain.planification.RecruteurEngagé;
import com.soat.planification_entretien.infrastructure.planification.repository.Entretien;
import com.soat.planification_entretien.infrastructure.planification.repository.EntretienCrud;
import org.springframework.stereotype.Repository;

@Repository
public class HibernateEntretienRepository implements EntretienRepository {
    private final EntretienCrud entretienCrud;
    private final CandidatCrud candidatCrud;
    private final RecruteurCrud recruteurCrud;

    public HibernateEntretienRepository(EntretienCrud entretienCrud, CandidatCrud candidatCrud, RecruteurCrud recruteurCrud) {
        this.entretienCrud = entretienCrud;
        this.candidatCrud = candidatCrud;
        this.recruteurCrud = recruteurCrud;
    }

    @Override
    public void save(com.soat.planification_entretien.domain.planification.Entretien entretien) {

        var jpaCandidat = candidatCrud.findById(entretien.getCandidat().id()).get();
        var jpaRecruteur = recruteurCrud.findById(entretien.getRecruteur().id()).get();

        var jpaEntretien = Entretien.of(jpaCandidat,
                jpaRecruteur, entretien.getHoraireEntretien());
        entretienCrud.save(jpaEntretien);
    }

    @Override
    public List<com.soat.planification_entretien.domain.planification.Entretien> findAll() {
        return entretienCrud.findAll().stream()
                .map(HibernateEntretienRepository::toEntretien)
                .toList();
    }

    @Override
    public com.soat.planification_entretien.domain.planification.Entretien findByCandidat(CandidatSuivi candidat) {
        var maybeEntretien = entretienCrud.findByCandidat_Email(candidat.email());

        return maybeEntretien
                .map(HibernateEntretienRepository::toEntretien)
                .orElse(null);
    }

    private static com.soat.planification_entretien.domain.planification.Entretien toEntretien(Entretien jpaEntretien) {
        return com.soat.planification_entretien.domain.planification.Entretien.of(
                jpaEntretien.getId(),
                new CandidatSuivi(jpaEntretien.getCandidat().getId(), jpaEntretien.getCandidat().getEmail(), new Profil(jpaEntretien.getCandidat().getLanguage(), jpaEntretien.getCandidat().getExperienceInYears())),
                new RecruteurEngagé(jpaEntretien.getRecruteur().getId(), jpaEntretien.getRecruteur().getEmail(), new Profil(jpaEntretien.getRecruteur().getLanguage(), jpaEntretien.getRecruteur().getExperienceInYears())),
                jpaEntretien.getHoraireEntretien());
    }
}

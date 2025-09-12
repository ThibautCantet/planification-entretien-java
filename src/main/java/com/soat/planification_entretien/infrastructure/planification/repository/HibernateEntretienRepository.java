package com.soat.planification_entretien.infrastructure.planification.repository;

import java.util.List;

import com.soat.planification_entretien.domain.Profil;
import com.soat.planification_entretien.domain.planification.CandidatSuivi;
import com.soat.planification_entretien.domain.planification.EntretienRepository;
import com.soat.planification_entretien.domain.planification.EtatEntretien;
import com.soat.planification_entretien.domain.planification.RecruteurPossible;
import com.soat.planification_entretien.infrastructure.preparation.repository.CandidatCrud;
import com.soat.planification_entretien.infrastructure.preparation.repository.RecruteurCrud;
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

        var jpaEntretien = Entretien.of(
                entretien.getId(),
                jpaCandidat.getId(),
                jpaRecruteur.getId(),
                entretien.getHoraireEntretien(),
                entretien.getStatus());
        entretienCrud.save(jpaEntretien);
    }

    @Override
    public List<com.soat.planification_entretien.domain.planification.Entretien> findAll() {
        return entretienCrud.findAll().stream()
                .map(this::toEntretien)
                .toList();
    }

    @Override
    public com.soat.planification_entretien.domain.planification.Entretien findByCandidat(CandidatSuivi candidatSuivi) {
        var candidat = candidatCrud.findByEmail(candidatSuivi.email());

        var maybeEntretien = entretienCrud.findByCandidatId(candidat.getId());

        return maybeEntretien
                .map(this::toEntretien)
                .orElse(null);
    }

    @Override
    public com.soat.planification_entretien.domain.planification.Entretien findById(int id) {
        return entretienCrud.findById(id)
                .map(this::toEntretien)
                .orElse(null);
    }

    private com.soat.planification_entretien.domain.planification.Entretien toEntretien(Entretien jpaEntretien) {
        var recruteur = recruteurCrud.findById(jpaEntretien.getRecruteurId()).get();
        var candidat = candidatCrud.findById(jpaEntretien.getCandidatId()).get();
        return com.soat.planification_entretien.domain.planification.Entretien.of(
                jpaEntretien.getId(),
                new CandidatSuivi(jpaEntretien.getCandidatId(), candidat.getEmail(), new Profil(candidat.getLanguage(), candidat.getExperienceInYears())),
                new RecruteurPossible(jpaEntretien.getRecruteurId(), recruteur.getEmail(),
                        new Profil(recruteur.getLanguage(), recruteur.getExperienceInYears()),
                        recruteur.estDisponible()),
                jpaEntretien.getHoraireEntretien(),
                EtatEntretien.valueOf(jpaEntretien.getEtatEntretien()));
    }
}

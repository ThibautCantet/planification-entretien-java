package com.soat.planification_entretien.infrastructure.repository;

import java.util.List;

import com.soat.planification_entretien.domain.candidat.Candidat;
import com.soat.planification_entretien.domain.recruteur.Recruteur;
import com.soat.planification_entretien.domain.entretien.EntretienRepository;
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
    public int save(com.soat.planification_entretien.domain.entretien.Entretien entretien) {

        var jpaCandidat = candidatCrud.findById(entretien.getCandidat().getId()).get();
        var jpaRecruteur = recruteurCrud.findById(entretien.getRecruteur().getId()).get();

        var jpaEntretien = Entretien.of(jpaCandidat,
                jpaRecruteur, entretien.getHoraireEntretien());
        Entretien saved = entretienCrud.save(jpaEntretien);

        return saved.getId();
    }

    @Override
    public List<com.soat.planification_entretien.domain.entretien.Entretien> findAll() {
        return entretienCrud.findAll().stream()
                .map(HibernateEntretienRepository::toEntretien)
                .toList();
    }

    @Override
    public com.soat.planification_entretien.domain.entretien.Entretien findByCandidat(Candidat candidat) {
        return entretienCrud.findByCandidatId(candidat.getId())
                .map(HibernateEntretienRepository::toEntretien)
                .orElse(null);
    }

    @Override
    public com.soat.planification_entretien.domain.entretien.Entretien findById(int id) {
        return entretienCrud.findById(id)
                .map(HibernateEntretienRepository::toEntretien)
                .orElse(null);
    }

    private static com.soat.planification_entretien.domain.entretien.Entretien toEntretien(com.soat.planification_entretien.infrastructure.repository.Entretien jpaEntretien) {
        return com.soat.planification_entretien.domain.entretien.Entretien.of(
                jpaEntretien.getId(),
                new com.soat.planification_entretien.domain.candidat.Candidat(jpaEntretien.getId(), jpaEntretien.getCandidat().getLanguage(), jpaEntretien.getCandidat().getEmail(), jpaEntretien.getCandidat().getExperienceInYears()),
                new Recruteur(jpaEntretien.getId(), jpaEntretien.getRecruteur().getLanguage(), jpaEntretien.getRecruteur().getEmail(), jpaEntretien.getRecruteur().getExperienceInYears()),
                jpaEntretien.getHoraireEntretien());
    }
}

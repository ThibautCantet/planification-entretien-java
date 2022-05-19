package com.soat.planification_entretien.infrastructure.repository;

import java.util.List;

import com.soat.planification_entretien.use_case.EntretienRepository;
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
    public void save(com.soat.planification_entretien.domain.model.Entretien entretien) {

        var jpaCandidat = candidatCrud.findById(entretien.getCandidat().getId()).get();
        var jpaRecruteur = recruteurCrud.findById(entretien.getRecruteur().getId()).get();

        var jpaEntretien = Entretien.of(jpaCandidat,
                jpaRecruteur, entretien.getHoraireEntretien());
        entretienCrud.save(jpaEntretien);
    }

    @Override
    public List<com.soat.planification_entretien.domain.model.Entretien> findAll() {
        return entretienCrud.findAll().stream()
                .map(HibernateEntretienRepository::toEntretien)
                .toList();
    }

    private static com.soat.planification_entretien.domain.model.Entretien toEntretien(com.soat.planification_entretien.infrastructure.repository.Entretien jpaEntretien) {
        return com.soat.planification_entretien.domain.model.Entretien.of(
                jpaEntretien.getId(),
                new com.soat.planification_entretien.domain.model.Candidat(jpaEntretien.getCandidat().getLanguage(), jpaEntretien.getCandidat().getEmail(), jpaEntretien.getCandidat().getExperienceInYears()),
                new com.soat.planification_entretien.domain.model.Recruteur(jpaEntretien.getRecruteur().getLanguage(), jpaEntretien.getRecruteur().getEmail(), jpaEntretien.getRecruteur().getExperienceInYears()),
                jpaEntretien.getHoraireEntretien());
    }
}

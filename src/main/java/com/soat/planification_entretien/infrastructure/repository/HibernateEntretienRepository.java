package com.soat.planification_entretien.infrastructure.repository;

import java.util.List;

import com.soat.planification_entretien.domain.candidat.entity.Candidat;
import com.soat.planification_entretien.domain.recruteur.command.entity.Recruteur;
import com.soat.planification_entretien.domain.entretien.command.repository.EntretienRepository;
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
    public int save(com.soat.planification_entretien.domain.entretien.command.entity.Entretien entretien) {

        var jpaCandidat = candidatCrud.findById(entretien.getCandidat().getId()).get();
        var jpaRecruteur = recruteurCrud.findById(entretien.getRecruteur().getId()).get();

        var jpaEntretien = Entretien.of(jpaCandidat,
                jpaRecruteur, entretien.getHoraireEntretien());
        Entretien saved = entretienCrud.save(jpaEntretien);

        return saved.getId();
    }

    @Override
    public com.soat.planification_entretien.domain.entretien.command.entity.Entretien findByCandidat(Candidat candidat) {
        return entretienCrud.findByCandidatId(candidat.getId())
                .map(HibernateEntretienRepository::toEntretien)
                .orElse(null);
    }

    @Override
    public com.soat.planification_entretien.domain.entretien.command.entity.Entretien findById(int id) {
        return entretienCrud.findById(id)
                .map(HibernateEntretienRepository::toEntretien)
                .orElse(null);
    }

    private static com.soat.planification_entretien.domain.entretien.command.entity.Entretien toEntretien(com.soat.planification_entretien.infrastructure.repository.Entretien jpaEntretien) {
        return com.soat.planification_entretien.domain.entretien.command.entity.Entretien.of(
                jpaEntretien.getId(),
                new Candidat(jpaEntretien.getId(), jpaEntretien.getCandidat().getLanguage(), jpaEntretien.getCandidat().getEmail(), jpaEntretien.getCandidat().getExperienceInYears()),
                Recruteur.create(jpaEntretien.getId().toString(), jpaEntretien.getRecruteur().getLanguage(), jpaEntretien.getRecruteur().getEmail(), jpaEntretien.getRecruteur().getExperienceInYears()),
                jpaEntretien.getHoraireEntretien());
    }
}

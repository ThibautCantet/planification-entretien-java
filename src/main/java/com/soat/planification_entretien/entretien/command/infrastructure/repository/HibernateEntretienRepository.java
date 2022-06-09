package com.soat.planification_entretien.entretien.command.infrastructure.repository;

import java.util.List;

import com.soat.planification_entretien.rendez_vous.command.domain.entity.Calendrier;
import com.soat.planification_entretien.entretien.command.domain.entity.Candidat;
import com.soat.planification_entretien.entretien.command.domain.entity.Recruteur;
import com.soat.planification_entretien.entretien.command.domain.repository.EntretienRepository;
import com.soat.planification_entretien.infrastructure.repository.CandidatCrud;
import com.soat.planification_entretien.infrastructure.repository.Entretien;
import com.soat.planification_entretien.infrastructure.repository.EntretienCrud;
import com.soat.planification_entretien.infrastructure.repository.RecruteurCrud;
import com.soat.planification_entretien.rendez_vous.command.repository.CalendrierRepository;
import org.springframework.stereotype.Repository;

import static java.util.Optional.*;

@Repository
public class HibernateEntretienRepository implements EntretienRepository {
    private final EntretienCrud entretienCrud;
    private final CandidatCrud candidatCrud;
    private final RecruteurCrud recruteurCrud;
    private final CalendrierRepository calendrierRepository;

    public HibernateEntretienRepository(EntretienCrud entretienCrud, CandidatCrud candidatCrud, RecruteurCrud recruteurCrud, CalendrierRepository calendrierRepository) {
        this.entretienCrud = entretienCrud;
        this.candidatCrud = candidatCrud;
        this.recruteurCrud = recruteurCrud;
        this.calendrierRepository = calendrierRepository;
    }

    @Override
    public int save(com.soat.planification_entretien.entretien.command.domain.entity.Entretien entretien) {

        var jpaCandidat = candidatCrud.findById(entretien.getCandidat().getId()).get();
        var jpaRecruteur = recruteurCrud.findById(entretien.getRecruteur().getId()).get();

        var jpaEntretien = Entretien.of(jpaCandidat,
                jpaRecruteur, entretien.getHoraireEntretien());
        Entretien saved = entretienCrud.save(jpaEntretien);

        return saved.getId();
    }

    @Override
    public com.soat.planification_entretien.entretien.command.domain.entity.Entretien findByCandidat(Candidat candidat) {
        return entretienCrud.findByCandidatId(candidat.getId())
                .map(this::toEntretien)
                .orElse(null);
    }

    @Override
    public com.soat.planification_entretien.entretien.command.domain.entity.Entretien findById(int id) {
        return entretienCrud.findById(id)
                .map(this::toEntretien)
                .orElse(null);
    }

    private com.soat.planification_entretien.entretien.command.domain.entity.Entretien toEntretien(com.soat.planification_entretien.infrastructure.repository.Entretien jpaEntretien) {
        Calendrier calendrier = calendrierRepository.findByRecruteur(jpaEntretien.getRecruteur().getEmail()).orElse(null);
        var rendezVous = ofNullable(calendrier)
                .map(c -> c.rendezVous())
                .orElse(List.of())
                .stream()
                .map(rdv -> new com.soat.planification_entretien.entretien.command.domain.entity.RendezVous(rdv.emailCandidat(), rdv.horaire()))
                .toList();

        return com.soat.planification_entretien.entretien.command.domain.entity.Entretien.of(
                jpaEntretien.getId(),
                new Candidat(jpaEntretien.getId(), jpaEntretien.getCandidat().getLanguage(), jpaEntretien.getCandidat().getEmail(), jpaEntretien.getCandidat().getExperienceInYears()),
                new Recruteur(jpaEntretien.getId().toString(), jpaEntretien.getRecruteur().getLanguage(), jpaEntretien.getRecruteur().getEmail(), jpaEntretien.getRecruteur().getExperienceInYears(), rendezVous),
                jpaEntretien.getHoraireEntretien());
    }
}

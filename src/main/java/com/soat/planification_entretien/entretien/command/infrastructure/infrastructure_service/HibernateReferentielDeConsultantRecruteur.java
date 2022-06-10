package com.soat.planification_entretien.entretien.command.infrastructure.infrastructure_service;

import java.util.List;

import com.soat.planification_entretien.entretien.command.domain.entity.Recruteur;
import com.soat.planification_entretien.entretien.command.infrastructure_service.ReferentielDeConsultantRecruteur;
import com.soat.planification_entretien.infrastructure.repository.RecruteurCrud;
import com.soat.planification_entretien.rendez_vous.command.domain.entity.Calendrier;
import com.soat.planification_entretien.rendez_vous.command.domain.entity.RendezVous;
import com.soat.planification_entretien.rendez_vous.command.repository.CalendrierRepository;
import org.springframework.stereotype.Repository;

@Repository
public class HibernateReferentielDeConsultantRecruteur implements ReferentielDeConsultantRecruteur {

    private final RecruteurCrud recruteurCrud;
    private final CalendrierRepository calendrierRepository;

    public HibernateReferentielDeConsultantRecruteur(RecruteurCrud recruteurCrud, CalendrierRepository calendrierRepository) {
        this.recruteurCrud = recruteurCrud;
        this.calendrierRepository = calendrierRepository;
    }

    @Override
    public List<Recruteur> findAll() {
        return recruteurCrud.findAll().stream()
                .map(this::toRecruteur)
                .toList();
    }

    private Recruteur toRecruteur(com.soat.planification_entretien.infrastructure.repository.Recruteur recruteur) {
        Calendrier calendrier = calendrierRepository.findByRecruteur(recruteur.getEmail()).orElse(new Calendrier(0, "", List.of()));

        var rendezVous = calendrier.rendezVous().stream()
                .map(rdv -> new com.soat.planification_entretien.entretien.command.domain.entity.RendezVous(rdv.emailCandidat(), rdv.horaire()))
                .toList();

        return new Recruteur(
                recruteur.getId(),
                recruteur.getLanguage(),
                recruteur.getEmail(),
                recruteur.getExperienceInYears(),
                rendezVous
        );
    }
}

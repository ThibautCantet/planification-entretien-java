package com.soat.planification_entretien.entretien.listener;

import com.soat.planification_entretien.cqrs.Event;
import com.soat.planification_entretien.cqrs.EventHandlerEvent;
import com.soat.planification_entretien.cqrs.EventHandlerVoid;
import com.soat.planification_entretien.entretien.command.domain.entity.Entretien;
import com.soat.planification_entretien.entretien.command.domain.repository.EntretienRepository;
import com.soat.planification_entretien.entretien.event.EntretienMisAJour;
import com.soat.planification_entretien.entretien.event.RendezVousAnnule;
import com.soat.planification_entretien.rendez_vous.command.domain.entity.Calendrier;
import com.soat.planification_entretien.rendez_vous.command.domain.entity.RendezVous;
import com.soat.planification_entretien.rendez_vous.command.repository.CalendrierRepository;
import org.springframework.stereotype.Service;

import static com.soat.planification_entretien.entretien.command.application.controller.Status.*;

@Service
public class ModifierRendezVous extends EventHandlerEvent<EntretienMisAJour> {
    private final EntretienRepository entretienRepository;
    private final CalendrierRepository calendrierRepository;

    public ModifierRendezVous(EntretienRepository entretienRepository, CalendrierRepository calendrierRepository) {
        this.entretienRepository = entretienRepository;
        this.calendrierRepository = calendrierRepository;
    }

    @Override
    public Event handle(EntretienMisAJour event) {
        if (event.status().equals(ANNULE.name())) {
            Entretien entretien = entretienRepository.findById(event.id());
            Calendrier calendrier = calendrierRepository.findByRecruteur(entretien.getEmailRecruteur()).get();
            calendrier.annulerRendezVous(new RendezVous(entretien.getEmailCandidat(), entretien.getHoraire()));

            calendrierRepository.save(calendrier);

            return new RendezVousAnnule(entretien.getId(), entretien.getHoraire());
        }
        return null;
    }

    @Override
    public Class listenTo() {
        return EntretienMisAJour.class;
    }
}

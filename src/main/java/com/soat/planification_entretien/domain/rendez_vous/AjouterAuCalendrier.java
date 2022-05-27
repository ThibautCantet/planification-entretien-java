package com.soat.planification_entretien.domain.rendez_vous;

import java.util.ArrayList;

import com.soat.planification_entretien.domain.entretien.entities.Calendrier;
import com.soat.planification_entretien.domain.entretien.event.EntretienPlanifie;
import com.soat.planification_entretien.cqrs.Event;
import com.soat.planification_entretien.cqrs.EventHandler;
import org.springframework.stereotype.Service;

@Service
public class AjouterAuCalendrier implements EventHandler<EntretienPlanifie> {
    private final CalendrierRepository calendrierRepository;

    public AjouterAuCalendrier(CalendrierRepository calendrierRepository) {
        this.calendrierRepository = calendrierRepository;
    }

    @Override
    public Event handle(EntretienPlanifie event) {
        Calendrier calendrier = calendrierRepository.findByRecruteur(event.emailRecruteur())
                .orElse(new Calendrier(null, event.emailRecruteur(), new ArrayList<>()));

        calendrier.add(new RendezVous(event.emailCandidat(), event.horaire()));

        calendrierRepository.save(calendrier);

        return null;
    }

    @Override
    public Class listenTo() {
        return EntretienPlanifie.class;
    }
}

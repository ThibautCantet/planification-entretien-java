package com.soat.planification_entretien.domain.rendez_vous;

import java.util.ArrayList;

import com.soat.planification_entretien.domain.entretien.Calendrier;
import com.soat.planification_entretien.domain.entretien.EntretienPlanifie;
import com.soat.planification_entretien.infrastructure.middleware.Listener;
import org.springframework.stereotype.Service;

@Service
public class AjouterAuCalendrier implements Listener<EntretienPlanifie> {
    private final CalendrierRepository calendrierRepository;

    public AjouterAuCalendrier(CalendrierRepository calendrierRepository) {
        this.calendrierRepository = calendrierRepository;
    }

    @Override
    public void onEvent(EntretienPlanifie event) {
        Calendrier calendrier = calendrierRepository.findByRecruteur(event.emailRecruteur())
                .orElse(new Calendrier(null, event.emailRecruteur(), new ArrayList<>()));

        calendrier.add(new RendezVous(event.emailCandidat(), event.horaire()));

        calendrierRepository.save(calendrier);
    }
}

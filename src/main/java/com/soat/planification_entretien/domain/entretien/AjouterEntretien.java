package com.soat.planification_entretien.domain.entretien;

import java.util.ArrayList;

import com.soat.planification_entretien.cqrs.EventHandler;
import com.soat.planification_entretien.domain.rendez_vous.CalendrierRepository;
import com.soat.planification_entretien.domain.rendez_vous.RendezVous;
import com.soat.planification_entretien.infrastructure.middleware.Event;
import org.springframework.stereotype.Service;

@Service
public class AjouterEntretien implements EventHandler<EntretienPlanifie> {
    private final EntretienRepository entretienRepository;
    private final CalendrierRepository calendrierRepository;

    public AjouterEntretien(EntretienRepository entretienRepository, CalendrierRepository calendrierRepository) {
        this.entretienRepository = entretienRepository;
        this.calendrierRepository = calendrierRepository;
    }

    @Override
    public Event handle(EntretienPlanifie event) {
        Entretien entretien = entretienRepository.findById(event.id());
        Calendrier calendrier = calendrierRepository.findByRecruteur(entretien.getRecruteur().getEmail())
                .orElse(new Calendrier(null, entretien.getEmailRecruteur(), new ArrayList<>()));

        RendezVous rendezVous = new RendezVous(entretien.getEmailCandidat(), entretien.getHoraireEntretien());
        calendrier.add(rendezVous);

        calendrierRepository.save(calendrier);

        return null;
    }

    @Override
    public Class listenTo() {
        return EntretienPlanifie.class;
    }
}

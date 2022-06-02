package com.soat.planification_entretien.domain.entretien;

import java.util.ArrayList;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.soat.planification_entretien.domain.rendez_vous.CalendrierRepository;
import com.soat.planification_entretien.domain.rendez_vous.RendezVous;
import com.soat.planification_entretien.infrastructure.middleware.Listener;
import org.springframework.stereotype.Service;

@Service
public class ProjectionCalendrierJson implements Listener<EntretienPlanifie> {
    private final CalendrierDAO calendrierDAO;
    private final EntretienRepository entretienRepository;
    private final CalendrierRepository calendrierRepository;

    public ProjectionCalendrierJson(CalendrierDAO calendrierDAO, EntretienRepository entretienRepository, CalendrierRepository calendrierRepository) {
        this.calendrierDAO = calendrierDAO;
        this.entretienRepository = entretienRepository;
        this.calendrierRepository = calendrierRepository;
    }

    @Override
    public void onEvent(EntretienPlanifie event) {
        Entretien entretien = entretienRepository.findById(event.id());
        Calendrier calendrier = calendrierRepository.findByRecruteur(entretien.getRecruteur().getEmail())
                .orElse(new Calendrier(null, entretien.getEmailRecruteur(), new ArrayList<>()));

        RendezVous rendezVous = new RendezVous(entretien.getEmailCandidat(), entretien.getHoraireEntretien());
        calendrier.add(rendezVous);

        try {
            calendrierDAO.save(calendrier);
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
    }
}

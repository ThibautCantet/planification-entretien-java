package com.soat.planification_entretien.entretien.listener;

import java.util.ArrayList;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.soat.planification_entretien.cqrs.EventHandlerVoid;
import com.soat.planification_entretien.rendez_vous.command.domain.entity.Calendrier;
import com.soat.planification_entretien.entretien.command.domain.entity.Entretien;
import com.soat.planification_entretien.rendez_vous.command.domain.entity.RendezVous;
import com.soat.planification_entretien.entretien.command.domain.repository.EntretienRepository;
import com.soat.planification_entretien.entretien.event.EntretienPlanifie;
import com.soat.planification_entretien.entretien.listener.dao.CalendrierDAO;
import com.soat.planification_entretien.rendez_vous.command.repository.CalendrierRepository;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

@Service
public class ProjectionCalendrierJson extends EventHandlerVoid<EntretienPlanifie> {
    private final CalendrierDAO calendrierDAO;
    private final EntretienRepository entretienRepository;
    private final CalendrierRepository calendrierRepository;

    public ProjectionCalendrierJson(@Qualifier("listener") CalendrierDAO calendrierDAO, EntretienRepository entretienRepository, CalendrierRepository calendrierRepository) {
        this.calendrierDAO = calendrierDAO;
        this.entretienRepository = entretienRepository;
        this.calendrierRepository = calendrierRepository;
    }

    @Override
    public void handle(EntretienPlanifie event) {
        Entretien entretien = entretienRepository.findById(event.id());
        Calendrier calendrier = calendrierRepository.findByRecruteur(entretien.getRecruteur().getEmail())
                .orElse(new Calendrier(null, entretien.getEmailRecruteur(), new ArrayList<>()));

        RendezVous rendezVous = new RendezVous(entretien.getEmailCandidat(), entretien.getHoraire());
        calendrier.add(rendezVous);

        try {
            calendrierDAO.save(calendrier);
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public Class listenTo() {
        return EntretienPlanifie.class;
    }
}

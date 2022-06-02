package com.soat.planification_entretien.domain.entretien.listener;

import com.soat.planification_entretien.cqrs.Command;
import com.soat.planification_entretien.cqrs.EventHandlerCommand;
import com.soat.planification_entretien.domain.entretien.event.EntretienPlanifie;
import com.soat.planification_entretien.domain.entretien.listener.dao.EntretienDAO;
import com.soat.planification_entretien.domain.entretien.listener.dto.Entretien;
import com.soat.planification_entretien.domain.rendez_vous.command.AjouterRendezVousCommand;
import org.springframework.stereotype.Service;

@Service
public class AjouterEntretien extends EventHandlerCommand<EntretienPlanifie> {
    private final EntretienDAO entretienDAO;

    public AjouterEntretien(EntretienDAO entretienDAO) {
        this.entretienDAO = entretienDAO;
    }

    @Override
    public Command handle(EntretienPlanifie event) {
        Entretien entretien = entretienDAO.findById(event.id());

        return new AjouterRendezVousCommand(entretien.emailRecruteur(), entretien.emailCandidat(), entretien.horaire());
    }

    @Override
    public Class listenTo() {
        return EntretienPlanifie.class;
    }
}

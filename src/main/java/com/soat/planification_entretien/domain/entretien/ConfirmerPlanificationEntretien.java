package com.soat.planification_entretien.domain.entretien;

import com.soat.planification_entretien.domain.entretien.event.EntretienPlanifie;
import com.soat.planification_entretien.cqrs.Event;
import com.soat.planification_entretien.cqrs.EventHandler;
import org.springframework.stereotype.Service;

@Service
public class ConfirmerPlanificationEntretien implements EventHandler<EntretienPlanifie> {
    private final EmailService emailService;

    public ConfirmerPlanificationEntretien(EmailService emailService) {
        this.emailService = emailService;
    }

    @Override
    public Event handle(EntretienPlanifie event) {
        emailService.envoyerUnEmailDeConfirmationAuCandidat(event.emailCandidat(), event.horaire());
        emailService.envoyerUnEmailDeConfirmationAuRecruteur(event.emailRecruteur(), event.horaire());

        return null;
    }

    @Override
    public Class listenTo() {
        return EntretienPlanifie.class;
    }
}

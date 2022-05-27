package com.soat.planification_entretien.domain.entretien;

import com.soat.planification_entretien.infrastructure.middleware.Listener;
import org.springframework.stereotype.Service;

@Service
public class ConfirmerPlanificationEntretien implements Listener<EntretienPlanifie> {
    private final EmailService emailService;

    public ConfirmerPlanificationEntretien(EmailService emailService) {
        this.emailService = emailService;
    }

    @Override
    public void onEvent(EntretienPlanifie event) {
        emailService.envoyerUnEmailDeConfirmationAuCandidat(event.emailCandidat(), event.horaire());
        emailService.envoyerUnEmailDeConfirmationAuRecruteur(event.emailRecruteur(), event.horaire());
    }
}

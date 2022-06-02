package com.soat.planification_entretien.domain.entretien;

import com.soat.planification_entretien.cqrs.EventHandler;
import com.soat.planification_entretien.infrastructure.middleware.Event;
import org.springframework.stereotype.Service;

@Service
public class EnvoyerEmails implements EventHandler<EntretienPlanifie> {
    private final EmailService emailService;

    public EnvoyerEmails(EmailService emailService) {
        this.emailService = emailService;
    }

    @Override
    public Event handle(EntretienPlanifie event) {
        emailService.envoyerUnEmailDeConfirmationAuCandidat(event.emailCandidat(), event.horaireEntretien());
        emailService.envoyerUnEmailDeConfirmationAuRecruteur(event.emailRecruteur(), event.horaireEntretien());

        return new EmailsEnvoyes();
    }

    @Override
    public Class listenTo() {
        return EntretienPlanifie.class;
    }
}

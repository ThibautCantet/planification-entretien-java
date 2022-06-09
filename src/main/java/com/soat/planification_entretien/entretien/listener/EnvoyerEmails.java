package com.soat.planification_entretien.entretien.listener;

import com.soat.planification_entretien.cqrs.Event;
import com.soat.planification_entretien.cqrs.EventHandlerEvent;
import com.soat.planification_entretien.entretien.event.EmailsEnvoyes;
import com.soat.planification_entretien.entretien.event.EntretienPlanifie;
import com.soat.planification_entretien.entretien.listener.service.EmailService;
import org.springframework.stereotype.Service;

@Service
public class EnvoyerEmails extends EventHandlerEvent<EntretienPlanifie> {
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
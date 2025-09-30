package com.soat.planification_entretien.infrastructure;

import java.time.LocalDateTime;

import com.soat.planification_entretien.application.use_case.output_port.EmailServicePort;
import org.springframework.stereotype.Service;

@Service
public class DummyEmailServiceAdapter implements EmailServicePort {
    @Override
    public void envoyerUnEmailDeConfirmationAuCandidat(String email, LocalDateTime horaire) {
        // appel SMTP pour envoyer un email
        System.out.println("Envoi d'un email de confirmation au candidat " + email + " pour un entretien le " + horaire);
    }

    @Override
    public void envoyerUnEmailDeConfirmationAuRecruteur(String email, LocalDateTime horaire) {

    }
}

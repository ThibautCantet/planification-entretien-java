package com.soat.planification_entretien.infrastructure;

import com.soat.planification_entretien.domain.EmailService;

import java.time.LocalDateTime;

public class FakeEmailService implements EmailService {
    private boolean unEmailDeConfirmationAEteEnvoyerAuCandidat = false;
    private boolean unEmailDeConfirmationAEteEnvoyerAuRecruteur = false;

    public void envoyerUnEmailDeConfirmationAuCandidat(String email, LocalDateTime horaire) {
        unEmailDeConfirmationAEteEnvoyerAuCandidat = true;
    }

    public boolean unEmailDeConfirmationAEteEnvoyerAuCandidat() {
        return unEmailDeConfirmationAEteEnvoyerAuCandidat;
    }

    public void envoyerUnEmailDeConfirmationAuRecruteur(String email, LocalDateTime horaire) {
        unEmailDeConfirmationAEteEnvoyerAuRecruteur = true;
    }

    public boolean unEmailDeConfirmationAEteEnvoyerAuRecruteur() {
        return unEmailDeConfirmationAEteEnvoyerAuRecruteur;
    }
}

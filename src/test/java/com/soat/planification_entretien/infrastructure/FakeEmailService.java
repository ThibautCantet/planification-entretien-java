package com.soat.planification_entretien.infrastructure;

import com.soat.planification_entretien.domain.EmailService;
import com.soat.planification_entretien.domain.HoraireEntretien;

public class FakeEmailService implements EmailService {
    private boolean unEmailDeConfirmationAEteEnvoyerAuCandidat = false;
    private boolean unEmailDeConfirmationAEteEnvoyerAuRecruteur = false;

    @Override
    public void envoyerUnEmailDeConfirmationAuCandidat(String email, HoraireEntretien horaire) {
        unEmailDeConfirmationAEteEnvoyerAuCandidat = true;
    }

    public boolean unEmailDeConfirmationAEteEnvoyerAuCandidat(String email, HoraireEntretien horaire) {
        return unEmailDeConfirmationAEteEnvoyerAuCandidat;
    }

    @Override
    public void envoyerUnEmailDeConfirmationAuRecruteur(String email, HoraireEntretien horaire) {
        unEmailDeConfirmationAEteEnvoyerAuRecruteur = true;
    }

    public boolean unEmailDeConfirmationAEteEnvoyerAuRecruteur(String email, HoraireEntretien horaire) {
        return unEmailDeConfirmationAEteEnvoyerAuRecruteur;
    }
}

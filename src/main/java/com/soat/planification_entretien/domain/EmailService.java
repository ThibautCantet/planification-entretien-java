package com.soat.planification_entretien.domain;

public interface EmailService {
    void envoyerUnEmailDeConfirmationAuCandidat(String email, HoraireEntretien horaire);

    void envoyerUnEmailDeConfirmationAuRecruteur(String email, HoraireEntretien horaire);
}

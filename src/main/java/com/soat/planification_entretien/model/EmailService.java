package com.soat.planification_entretien.model;

public interface EmailService {
    void envoyerUnEmailDeConfirmationAuCandidat(String email, HoraireEntretien horaire);

    void envoyerUnEmailDeConfirmationAuRecruteur(String email, HoraireEntretien horaire);
}

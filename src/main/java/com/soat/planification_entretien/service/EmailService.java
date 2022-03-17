package com.soat.planification_entretien.service;

public interface EmailService {
    void envoyerConfirmationAuCandidat(String emailCandidat);

    void envoyerConfirmationAuRecruteur(String emailRecruteur);
}

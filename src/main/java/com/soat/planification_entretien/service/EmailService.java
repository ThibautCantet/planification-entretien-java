package com.soat.planification_entretien.service;

import com.soat.planification_entretien.model.HoraireEntretien;

public interface EmailService {
    void envoyerUnEmailDeConfirmationAuCandidat(String email, HoraireEntretien horaire);

    void envoyerUnEmailDeConfirmationAuRecruteur(String email, HoraireEntretien horaire);
}

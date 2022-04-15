package com.soat.planification_entretien.domain;

import java.time.LocalDateTime;

public interface EmailService {
    void envoyerUnEmailDeConfirmationAuCandidat(String email, LocalDateTime horaire);

    void envoyerUnEmailDeConfirmationAuRecruteur(String email, LocalDateTime horaire);
}

package com.soat.planification_entretien.application.use_case.output_port;

import java.time.LocalDateTime;

public interface EmailServicePort {
    void envoyerUnEmailDeConfirmationAuCandidat(String email, LocalDateTime horaire);

    void envoyerUnEmailDeConfirmationAuRecruteur(String email, LocalDateTime horaire);
}

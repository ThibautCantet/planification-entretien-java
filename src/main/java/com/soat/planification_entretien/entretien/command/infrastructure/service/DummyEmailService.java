package com.soat.planification_entretien.entretien.command.infrastructure.service;

import java.time.LocalDateTime;

import com.soat.planification_entretien.entretien.query.domain.port.service.EmailService;
import org.springframework.stereotype.Service;

@Service
public class DummyEmailService implements EmailService {
    @Override
    public void envoyerUnEmailDeConfirmationAuCandidat(String email, LocalDateTime horaire) {

    }

    @Override
    public void envoyerUnEmailDeConfirmationAuRecruteur(String email, LocalDateTime horaire) {

    }
}

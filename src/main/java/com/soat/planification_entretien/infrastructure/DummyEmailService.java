package com.soat.planification_entretien.infrastructure;

import java.time.LocalDateTime;

import com.soat.planification_entretien.domain.entretien.listener.EmailService;
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

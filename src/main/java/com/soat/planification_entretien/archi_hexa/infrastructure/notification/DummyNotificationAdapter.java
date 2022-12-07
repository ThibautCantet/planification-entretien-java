package com.soat.planification_entretien.archi_hexa.infrastructure.notification;

import java.time.LocalDateTime;

import com.soat.planification_entretien.archi_hexa.domain.port.NotificationPort;
import org.springframework.stereotype.Service;

@Service
public class DummyNotificationAdapter implements NotificationPort {
    @Override
    public void envoyerUnEmailDeConfirmationAuCandidat(String email, LocalDateTime horaire) {

    }

    @Override
    public void envoyerUnEmailDeConfirmationAuRecruteur(String email, LocalDateTime horaire) {

    }
}

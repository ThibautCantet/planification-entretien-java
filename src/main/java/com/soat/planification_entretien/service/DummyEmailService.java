package com.soat.planification_entretien.service;

import com.soat.planification_entretien.model.HoraireEntretien;
import org.springframework.stereotype.Service;

@Service
public class DummyEmailService implements EmailService {
    @Override
    public void envoyerUnEmailDeConfirmationAuCandidat(String email, HoraireEntretien horaire) {

    }

    @Override
    public void envoyerUnEmailDeConfirmationAuRecruteur(String email, HoraireEntretien horaire) {

    }
}

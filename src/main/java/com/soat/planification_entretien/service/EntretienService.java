package com.soat.planification_entretien.service;

import java.time.LocalDate;
import java.time.LocalDateTime;

import com.soat.planification_entretien.model.Candidat;
import com.soat.planification_entretien.model.Entretien;
import com.soat.planification_entretien.model.Recruteur;
import com.soat.planification_entretien.repository.EntretienRepository;

public class EntretienService {
    public EntretienService(EntretienRepository entretienRepository, EmailService emailService) {

    }

    public Entretien planifier(Candidat candidat, Recruteur recruteur, LocalDateTime disponibiliteCandidat, LocalDate disponibiliteRecruteur) {
        return null;
    }
}

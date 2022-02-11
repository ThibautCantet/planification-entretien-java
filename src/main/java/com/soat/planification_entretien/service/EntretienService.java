package com.soat.planification_entretien.service;

import java.time.LocalDate;
import java.time.LocalDateTime;

import com.soat.planification_entretien.model.Candidat;
import com.soat.planification_entretien.model.Entretien;
import com.soat.planification_entretien.model.Recruteur;
import com.soat.planification_entretien.repository.EntretienRepository;

public class EntretienService {
    private final EntretienRepository entretienRepository;
    private final EmailService emailService;

    public EntretienService(EntretienRepository entretienRepository, EmailService emailService) {

        this.entretienRepository = entretienRepository;
        this.emailService = emailService;
    }

    public Entretien planifier(Candidat candidat, Recruteur recruteur, LocalDateTime disponibiliteCandidat, LocalDate disponibiliteRecruteur) {
        LocalDate dateDisponibiliteCandidat = LocalDate.of(disponibiliteCandidat.getYear(), disponibiliteCandidat.getMonth(), disponibiliteCandidat.getDayOfMonth());
        if (dateDisponibiliteCandidat.equals(disponibiliteRecruteur)) {
            if (candidat.language().equals(recruteur.language())) {
                Entretien entretien = new Entretien(disponibiliteCandidat, candidat.email(), recruteur.email());

                emailService.sendToCandidat(candidat.email());
                emailService.sendToRecruteur(recruteur.email());

                return entretienRepository.save(entretien);
            }
        }
        return null;
    }
}

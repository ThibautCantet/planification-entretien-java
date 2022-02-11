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
        if (isDateOk(disponibiliteRecruteur, dateDisponibiliteCandidat) && isSameLanguage(candidat, recruteur) && isRecruteurMoreExperienced(candidat, recruteur)) {
            Entretien entretien = new Entretien(disponibiliteCandidat, candidat.email(), recruteur.email());

            emailService.sendToCandidat(candidat.email());
            emailService.sendToRecruteur(recruteur.email());

            return entretienRepository.save(entretien);
        }
        return null;
    }

    private boolean isDateOk(LocalDate disponibiliteRecruteur, LocalDate dateDisponibiliteCandidat) {
        return dateDisponibiliteCandidat.equals(disponibiliteRecruteur);
    }

    private boolean isSameLanguage(Candidat candidat, Recruteur recruteur) {
        return candidat.language().equals(recruteur.language());
    }

    private boolean isRecruteurMoreExperienced(Candidat candidat, Recruteur recruteur) {
        return recruteur.xp() > candidat.xp();
    }
}

package com.soat.planification_entretien.service;

import com.soat.planification_entretien.model.*;
import com.soat.planification_entretien.repository.EntretienRepository;

import java.time.LocalDate;

public class EntretienService {
    private final EntretienRepository entretienRepository;
    private final EmailService emailService;

    public EntretienService(EntretienRepository entretienRepository, EmailService emailService) {
        this.entretienRepository = entretienRepository;
        this.emailService = emailService;
    }

    public ResultatPlanificationEntretien planifier(Candidat candidat, Disponibilite disponibiliteDuCandidat, Recruteur recruteur, LocalDate dateDeDisponibiliteDuRecruteur) {
        HoraireEntretien horaireEntretien = new HoraireEntretien(disponibiliteDuCandidat.horaire());

        Entretien entretien = new Entretien(candidat, recruteur);
        ResultatPlanificationEntretien resultatPlanificationEntretien = entretien.planifier(disponibiliteDuCandidat, dateDeDisponibiliteDuRecruteur);

        if (resultatPlanificationEntretien instanceof EntretienPlanifie) {
            entretienRepository.save(entretien);
            emailService.envoyerUnEmailDeConfirmationAuCandidat(candidat.getEmail(), horaireEntretien);
            emailService.envoyerUnEmailDeConfirmationAuRecruteur(recruteur.getEmail(), horaireEntretien);
        }
        return resultatPlanificationEntretien;
    }
}

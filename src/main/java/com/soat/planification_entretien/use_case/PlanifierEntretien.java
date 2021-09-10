package com.soat.planification_entretien.use_case;

import com.soat.planification_entretien.domain.*;

import java.time.LocalDate;

public class PlanifierEntretien {
    private final EntretienRepository entretienRepository;
    private final EmailService emailService;

    public PlanifierEntretien(EntretienRepository entretienRepository, EmailService emailService) {
        this.entretienRepository = entretienRepository;
        this.emailService = emailService;
    }

    public ResultatPlanificationEntretien execute(Candidat candidat, Disponibilite disponibiliteDuCandidat, Recruteur recruteur, LocalDate dateDeDisponibiliteDuRecruteur) {
        HoraireEntretien horaireEntretien = new HoraireEntretien(disponibiliteDuCandidat.horaire());

        Entretien entretien = new Entretien(candidat, recruteur);
        ResultatPlanificationEntretien resultatPlanificationEntretien = entretien.planifier(disponibiliteDuCandidat, dateDeDisponibiliteDuRecruteur);

        if (resultatPlanificationEntretien instanceof EntretienPlanifie) {
            entretienRepository.save(entretien);
            emailService.envoyerUnEmailDeConfirmationAuCandidat(candidat.email(), horaireEntretien);
            emailService.envoyerUnEmailDeConfirmationAuRecruteur(recruteur.email(), horaireEntretien);
        }
        return resultatPlanificationEntretien;
    }
}

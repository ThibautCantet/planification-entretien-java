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

    public void execute(Candidat candidat, Disponibilite disponibiliteDuCandidat, Recruteur recruteur, LocalDate dateDeDisponibiliteDuRecruteur) {
        HoraireEntretien horaireEntretien = new HoraireEntretien(disponibiliteDuCandidat.horaire());
        entretienRepository.save(new Entretien(candidat, recruteur, horaireEntretien));
        emailService.envoyerUnEmailDeConfirmationAuCandidat(candidat.email(), horaireEntretien);
        emailService.envoyerUnEmailDeConfirmationAuRecruteur(recruteur.email(), horaireEntretien);
    }
}

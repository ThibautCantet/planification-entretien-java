package com.soat.planification_entretien.use_case;

import com.soat.planification_entretien.domain.Candidat;
import com.soat.planification_entretien.domain.*;
import com.soat.planification_entretien.event.EntretienPlanifie;
import com.soat.planification_entretien.event.ResultatPlanificationEntretien;
import com.soat.planification_entretien.domain.Recruteur;

import java.util.UUID;

public class PlanifierEntretien {
    private final EntretienRepository entretienRepository;
    private final EmailService emailService;
    private final CandidatRepository candidatRepository;
    private final RecruteurRepository recruteurRepository;

    public PlanifierEntretien(EntretienRepository entretienRepository, EmailService emailService, CandidatRepository candidatRepository, RecruteurRepository recruteurRepository) {
        this.entretienRepository = entretienRepository;
        this.emailService = emailService;
        this.candidatRepository = candidatRepository;
        this.recruteurRepository = recruteurRepository;
    }

    public ResultatPlanificationEntretien execute(PlanifierEntretienCommand candidatEntretienCommand) {
        final UUID id = entretienRepository.next();
        final Entretien entretien = new Entretien(id, candidatEntretienCommand.candidatId(), candidatEntretienCommand.recruteurId());
        final ResultatPlanificationEntretien resultatPlanificationEntretien = entretien.planifier(candidatEntretienCommand.disponibiliteDuCandidat(), candidatEntretienCommand.dateDeDisponibiliteDuRecruteur());

        if (resultatPlanificationEntretien instanceof EntretienPlanifie) {
            entretienRepository.save(entretien);

            final Candidat candidat = candidatRepository.findById(candidatEntretienCommand.candidatId());
            emailService.envoyerUnEmailDeConfirmationAuCandidat(candidat.email(), candidatEntretienCommand.disponibiliteDuCandidat());

            final Recruteur recruteur = recruteurRepository.findById(candidatEntretienCommand.recruteurId());
            emailService.envoyerUnEmailDeConfirmationAuRecruteur(recruteur.email(), candidatEntretienCommand.disponibiliteDuCandidat());
        }
        return resultatPlanificationEntretien;
    }
}

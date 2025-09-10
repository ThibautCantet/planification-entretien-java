package com.soat.planification_entretien.use_case.planification;

import java.time.LocalDateTime;

import com.soat.planification_entretien.domain.preparation.Candidat;
import com.soat.planification_entretien.domain.planification.EmailService;
import com.soat.planification_entretien.domain.planification.Entretien;
import com.soat.planification_entretien.domain.planification.EntretienRepository;
import com.soat.planification_entretien.domain.preparation.Recruteur;
import org.springframework.stereotype.Service;

@Service
public class PlanifierEntretien {
    private final EntretienRepository entretienRepository;
    private final EmailService emailService;

    public PlanifierEntretien(EntretienRepository entretienRepository, EmailService emailService) {
        this.entretienRepository = entretienRepository;
        this.emailService = emailService;
    }

    public boolean execute(Candidat candidat, Recruteur recruteur, LocalDateTime dateEtHeureDisponibiliteDuCandidat, LocalDateTime dateEtHeureDisponibiliteDuRecruteur) {
        try {
            var entretien = Entretien.of(candidat, recruteur, dateEtHeureDisponibiliteDuRecruteur, dateEtHeureDisponibiliteDuCandidat);
            entretienRepository.save(entretien);
            emailService.envoyerUnEmailDeConfirmationAuCandidat(candidat.getEmail(), dateEtHeureDisponibiliteDuCandidat);
            emailService.envoyerUnEmailDeConfirmationAuRecruteur(recruteur.getEmail(), dateEtHeureDisponibiliteDuCandidat);
            return true;
        } catch (Exception e) {
            return false;
        }
    }

}

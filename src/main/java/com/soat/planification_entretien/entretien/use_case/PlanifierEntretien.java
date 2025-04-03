package com.soat.planification_entretien.entretien.use_case;

import java.time.LocalDateTime;

import com.soat.planification_entretien.entretien.domain.Candidat;
import com.soat.planification_entretien.entretien.domain.ConsultantRecruteur;
import com.soat.planification_entretien.entretien.domain.EmailService;
import com.soat.planification_entretien.entretien.domain.Entretien;
import com.soat.planification_entretien.entretien.domain.EntretienRepository;
import org.springframework.stereotype.Service;

@Service
public class PlanifierEntretien {
    private final EntretienRepository entretienRepository;
    private final EmailService emailService;

    public PlanifierEntretien(EntretienRepository entretienRepository, EmailService emailService) {
        this.entretienRepository = entretienRepository;
        this.emailService = emailService;
    }

    public boolean execute(Candidat candidat, ConsultantRecruteur recruteur, LocalDateTime dateEtHeureDisponibiliteDuCandidat, LocalDateTime dateEtHeureDisponibiliteDuRecruteur) {
        Entretien entretien = new Entretien(candidat, recruteur);
        if (entretien.planifier(dateEtHeureDisponibiliteDuCandidat, dateEtHeureDisponibiliteDuRecruteur)) {
            entretienRepository.save(entretien);
            emailService.envoyerUnEmailDeConfirmationAuCandidat(candidat.email(), dateEtHeureDisponibiliteDuCandidat);
            emailService.envoyerUnEmailDeConfirmationAuRecruteur(recruteur.email(), dateEtHeureDisponibiliteDuCandidat);
            return true;
        }
        return false;
    }

}

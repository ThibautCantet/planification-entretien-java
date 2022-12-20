package com.soat.planification_entretien.use_case;

import java.time.LocalDateTime;

import com.soat.planification_entretien.domain.candidat.Candidat;
import com.soat.planification_entretien.domain.entretien.EmailService;
import com.soat.planification_entretien.domain.entretien.Entretien;
import com.soat.planification_entretien.domain.entretien.EntretienRepository;
import com.soat.planification_entretien.domain.recruteur.Recruteur;
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
        Entretien entretien = new Entretien(candidat, recruteur);
        if (entretien.planifier(dateEtHeureDisponibiliteDuCandidat, dateEtHeureDisponibiliteDuRecruteur)) {
            entretienRepository.save(entretien);
            emailService.envoyerUnEmailDeConfirmationAuCandidat(candidat.getAdresseEmail(), dateEtHeureDisponibiliteDuCandidat);
            emailService.envoyerUnEmailDeConfirmationAuRecruteur(recruteur.getEmail(), dateEtHeureDisponibiliteDuCandidat);
            return true;
        }
        return false;
    }

}

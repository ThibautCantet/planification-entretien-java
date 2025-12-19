package com.soat.planification_entretien.use_case;

import java.time.LocalDateTime;

import com.soat.planification_entretien.domain.CandidatPort;
import com.soat.planification_entretien.domain.EmailService;
import com.soat.planification_entretien.domain.Candidat;
import com.soat.planification_entretien.domain.Entretien;
import com.soat.planification_entretien.domain.EntretienPort;
import com.soat.planification_entretien.domain.Recruteur;
import com.soat.planification_entretien.domain.RecruteurPort;
import org.springframework.stereotype.Service;

@Service
public class PlanifierEntretien {
    private final CandidatPort candidatPort;
    private final RecruteurPort recruteurPort;
    private final EntretienPort entretienPort;
    private final EmailService emailService;

    public PlanifierEntretien(CandidatPort candidatPort, RecruteurPort recruteurPort, EntretienPort entretienPort, EmailService emailService) {
        this.candidatPort = candidatPort;
        this.recruteurPort = recruteurPort;
        this.entretienPort = entretienPort;
        this.emailService = emailService;
    }

    public boolean execute(int candidatId, int recruteurId, LocalDateTime dateEtHeureDisponibiliteDuCandidat, LocalDateTime dateEtHeureDisponibiliteDuRecruteur) {
        Candidat candidat = candidatPort.findById(candidatId).get();
        Recruteur recruteur = recruteurPort.findById(recruteurId).get();

        var entretien = Entretien.of(candidat, recruteur);
        if (entretien.planifier(dateEtHeureDisponibiliteDuCandidat, dateEtHeureDisponibiliteDuRecruteur)) {
            entretienPort.save(entretien);
            emailService.envoyerUnEmailDeConfirmationAuCandidat(candidat.getEmail(), dateEtHeureDisponibiliteDuCandidat);
            emailService.envoyerUnEmailDeConfirmationAuRecruteur(recruteur.getEmail(), dateEtHeureDisponibiliteDuCandidat);
            return true;
        }
        return false;
    }

}

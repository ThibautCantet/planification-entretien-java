package com.soat.planification_entretien.application.use_case;

import java.time.LocalDateTime;

import com.soat.planification_entretien.application.use_case.output_port.CandidatPort;
import com.soat.planification_entretien.application.use_case.output_port.EntretienPort;
import com.soat.planification_entretien.application.use_case.output_port.RecruteurPort;
import com.soat.planification_entretien.domain.model.Candidat;
import com.soat.planification_entretien.domain.model.Entretien;
import com.soat.planification_entretien.domain.model.Recruteur;
import com.soat.planification_entretien.application.use_case.output_port.EmailServicePort;
import org.springframework.stereotype.Service;

@Service
public class PlanifierEntretien {
    private final CandidatPort candidatPort;
    private final RecruteurPort recruteurPort;
    private final EntretienPort entretienPort;
    private final EmailServicePort emailServicePort;

    public PlanifierEntretien(CandidatPort candidatPort, RecruteurPort recruteurPort, EntretienPort entretienPort, EmailServicePort emailServicePort) {
        this.candidatPort = candidatPort;
        this.recruteurPort = recruteurPort;
        this.entretienPort = entretienPort;
        this.emailServicePort = emailServicePort;
    }

    public boolean execute(int candidatId, int recruteurId, LocalDateTime dateEtHeureDisponibiliteDuCandidat, LocalDateTime dateEtHeureDisponibiliteDuRecruteur) {
        Candidat candidat = candidatPort.findById(candidatId).get();
        Recruteur recruteur = recruteurPort.findById(recruteurId).get();

        var entretien = Entretien.of(candidat, recruteur);
        boolean estPlanifiable = entretien.planifier(dateEtHeureDisponibiliteDuCandidat, dateEtHeureDisponibiliteDuRecruteur);
        if (estPlanifiable) {
            entretienPort.save(entretien);
            emailServicePort.envoyerUnEmailDeConfirmationAuCandidat(candidat.getEmail(), dateEtHeureDisponibiliteDuCandidat);
            emailServicePort.envoyerUnEmailDeConfirmationAuRecruteur(recruteur.getEmail(), dateEtHeureDisponibiliteDuCandidat);
            return true;
        }
        return false;
    }
}

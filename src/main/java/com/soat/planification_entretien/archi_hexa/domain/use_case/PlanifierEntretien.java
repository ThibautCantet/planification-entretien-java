package com.soat.planification_entretien.archi_hexa.domain.use_case;

import com.soat.planification_entretien.archi_hexa.domain.port.NotificationPort;
import com.soat.planification_entretien.archi_hexa.domain.model.Candidat;
import com.soat.planification_entretien.archi_hexa.domain.model.Entretien;
import com.soat.planification_entretien.archi_hexa.domain.model.Recruteur;
import com.soat.planification_entretien.archi_hexa.domain.port.CandidatPort;
import com.soat.planification_entretien.archi_hexa.domain.port.EntretienPort;
import com.soat.planification_entretien.archi_hexa.domain.port.RecruteurPort;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
public class PlanifierEntretien {
    private final CandidatPort candidatPort;
    private final RecruteurPort recruteurPort;
    private final EntretienPort entretienPort;
    private final NotificationPort notificationPort;

    public PlanifierEntretien(CandidatPort candidatPort, RecruteurPort recruteurPort, EntretienPort entretienPort, NotificationPort notificationPort) {
        this.candidatPort = candidatPort;
        this.recruteurPort = recruteurPort;
        this.entretienPort = entretienPort;
        this.notificationPort = notificationPort;
    }

    public boolean planifier(int candidatId, int recruteurId, LocalDateTime dateEtHeureDisponibiliteDuCandidat, LocalDateTime dateEtHeureDisponibiliteDuRecruteur) {
        Candidat candidat = candidatPort.findById(candidatId).get();
        Recruteur recruteur = recruteurPort.findById(recruteurId).get();

        if (recruteur.language().equals(candidat.language())
                && recruteur.experienceInYears() > candidat.experienceInYears()
                && dateEtHeureDisponibiliteDuCandidat.equals(dateEtHeureDisponibiliteDuRecruteur)) {
            Entretien entretien = new Entretien(candidat, recruteur, dateEtHeureDisponibiliteDuRecruteur);
            entretienPort.save(entretien);
            notificationPort.envoyerUnEmailDeConfirmationAuCandidat(candidat.email(), dateEtHeureDisponibiliteDuCandidat);
            notificationPort.envoyerUnEmailDeConfirmationAuRecruteur(recruteur.email(), dateEtHeureDisponibiliteDuCandidat);
            return true;
        }
        return false;
    }
}

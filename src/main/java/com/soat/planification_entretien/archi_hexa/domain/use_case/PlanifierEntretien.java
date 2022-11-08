package com.soat.planification_entretien.archi_hexa.domain.use_case;

import com.soat.planification_entretien.archi_hexa.domain.EmailService;
import com.soat.planification_entretien.archi_hexa.domain.entity.Candidat;
import com.soat.planification_entretien.archi_hexa.domain.port.CandidatPort;
import com.soat.planification_entretien.archi_hexa.infrastructure.jpa.model.JpaEntretien;
import com.soat.planification_entretien.archi_hexa.infrastructure.jpa.model.JpaRecruteur;
import com.soat.planification_entretien.archi_hexa.infrastructure.jpa.repository.EntretienRepository;
import com.soat.planification_entretien.archi_hexa.infrastructure.jpa.repository.RecruteurRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
public class PlanifierEntretien {
    private final CandidatPort candidatPort;
    private final RecruteurRepository recruteurRepository;
    private final EntretienRepository entretienRepository;
    private final EmailService emailService;

    public PlanifierEntretien(CandidatPort candidatPort, RecruteurRepository recruteurRepository, EntretienRepository entretienRepository, EmailService emailService) {
        this.candidatPort = candidatPort;
        this.recruteurRepository = recruteurRepository;
        this.entretienRepository = entretienRepository;
        this.emailService = emailService;
    }

    public boolean execute(int candidatId, int recruteurId, LocalDateTime dateEtHeureDisponibiliteDuCandidat, LocalDateTime dateEtHeureDisponibiliteDuRecruteur) {
        Candidat candidat = candidatPort.findById(candidatId).get();
        JpaRecruteur recruteur = recruteurRepository.findById(recruteurId).get();

        if (recruteur.getLanguage().equals(candidat.language())
                && recruteur.getExperienceInYears() > candidat.experienceInYears()
                && dateEtHeureDisponibiliteDuCandidat.equals(dateEtHeureDisponibiliteDuRecruteur)) {
            JpaEntretien entretien = JpaEntretien.of(candidat, recruteur, dateEtHeureDisponibiliteDuRecruteur);
            entretienRepository.save(entretien);
            emailService.envoyerUnEmailDeConfirmationAuCandidat(candidat.email(), dateEtHeureDisponibiliteDuCandidat);
            emailService.envoyerUnEmailDeConfirmationAuRecruteur(recruteur.getEmail(), dateEtHeureDisponibiliteDuCandidat);
            return true;
        }
        return false;
    }
}

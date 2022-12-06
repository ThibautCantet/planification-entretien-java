package com.soat.planification_entretien.archi_hexa.domain.use_case;

import java.time.LocalDateTime;

import com.soat.planification_entretien.archi_hexa.domain.EmailService;
import com.soat.planification_entretien.archi_hexa.infrastructure.model.Candidat;
import com.soat.planification_entretien.archi_hexa.infrastructure.model.Entretien;
import com.soat.planification_entretien.archi_hexa.infrastructure.model.Recruteur;
import com.soat.planification_entretien.archi_hexa.infrastructure.repository.CandidatRepository;
import com.soat.planification_entretien.archi_hexa.infrastructure.repository.EntretienRepository;
import com.soat.planification_entretien.archi_hexa.infrastructure.repository.RecruteurRepository;
import org.springframework.stereotype.Service;

@Service
public class PlanifierEntretien {
    private final CandidatRepository candidatRepository;
    private final RecruteurRepository recruteurRepository;
    private final EntretienRepository entretienRepository;
    private final EmailService emailService;

    public PlanifierEntretien(CandidatRepository candidatRepository, RecruteurRepository recruteurRepository, EntretienRepository entretienRepository, EmailService emailService) {
        this.candidatRepository = candidatRepository;
        this.recruteurRepository = recruteurRepository;
        this.entretienRepository = entretienRepository;
        this.emailService = emailService;
    }

    public boolean planifier(int candidatId, int recruteurId, LocalDateTime dateEtHeureDisponibiliteDuCandidat, LocalDateTime dateEtHeureDisponibiliteDuRecruteur) {
        Candidat candidat = candidatRepository.findById(candidatId).get();
        Recruteur recruteur = recruteurRepository.findById(recruteurId).get();

        if (recruteur.getLanguage().equals(candidat.getLanguage())
                && recruteur.getExperienceInYears() > candidat.getExperienceInYears()
                && dateEtHeureDisponibiliteDuCandidat.equals(dateEtHeureDisponibiliteDuRecruteur)) {
            Entretien entretien = Entretien.of(candidat, recruteur, dateEtHeureDisponibiliteDuRecruteur);
            entretienRepository.save(entretien);
            emailService.envoyerUnEmailDeConfirmationAuCandidat(candidat.getEmail(), dateEtHeureDisponibiliteDuCandidat);
            emailService.envoyerUnEmailDeConfirmationAuRecruteur(recruteur.getEmail(), dateEtHeureDisponibiliteDuCandidat);
            return true;
        }
        return false;
    }
}

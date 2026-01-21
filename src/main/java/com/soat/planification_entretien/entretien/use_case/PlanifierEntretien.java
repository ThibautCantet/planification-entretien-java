package com.soat.planification_entretien.entretien.use_case;

import java.time.LocalDateTime;
import java.util.Optional;

import com.soat.planification_entretien.candidat.domain.CandidatProspect;
import com.soat.planification_entretien.candidat.domain.CandidatRepository;
import com.soat.planification_entretien.entretien.domain.aggregate.Candidat;
import com.soat.planification_entretien.entretien.domain.application_service.EmailService;
import com.soat.planification_entretien.entretien.domain.aggregate.Entretien;
import com.soat.planification_entretien.entretien.domain.aggregate.EntretienRepository;
import com.soat.planification_entretien.entretien.domain.aggregate.RecruteurPlanifié;
import com.soat.planification_entretien.entretien.domain.domain_service.RecruteurDisponible;
import org.springframework.stereotype.Service;

@Service
public class PlanifierEntretien {
    private final EntretienRepository entretienRepository;
    private final EmailService emailService;
    private final CandidatRepository candidatRepository;
    private final RecruteurDisponible trouverRecruteurDisponible;

    public PlanifierEntretien(EntretienRepository entretienRepository, CandidatRepository candidatRepository, EmailService emailService, RecruteurDisponible trouverRecruteurDisponible) {
        this.entretienRepository = entretienRepository;
        this.candidatRepository = candidatRepository;
        this.emailService = emailService;
        this.trouverRecruteurDisponible = trouverRecruteurDisponible;
    }

    public boolean execute(int candidatId, LocalDateTime dateEtHeureDisponibiliteDuCandidat) {
        Optional<CandidatProspect> candidat = candidatRepository.findById(candidatId);

        var candidatProspect = candidat.get();
        var candidatTrouvé = new Candidat(candidatProspect.getId(), candidatProspect.getLanguage(), candidatProspect.getEmail(), candidatProspect.getExperienceInYears());
        RecruteurPlanifié recruteur = trouverRecruteurDisponible.trouver(candidatTrouvé);
        if (recruteur == null) {
            return false;
        }

        var entretien = new Entretien(candidatTrouvé, recruteur);
        if (entretien.planifier(dateEtHeureDisponibiliteDuCandidat)) {
            entretienRepository.save(entretien);
            emailService.envoyerUnEmailDeConfirmationAuCandidat(candidatTrouvé.email(), dateEtHeureDisponibiliteDuCandidat);
            emailService.envoyerUnEmailDeConfirmationAuRecruteur(recruteur.email(), dateEtHeureDisponibiliteDuCandidat);
            return true;
        }
        return false;
    }

}

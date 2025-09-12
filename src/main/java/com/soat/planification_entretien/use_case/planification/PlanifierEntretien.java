package com.soat.planification_entretien.use_case.planification;

import java.time.LocalDateTime;

import com.soat.planification_entretien.domain.Profil;
import com.soat.planification_entretien.domain.planification.CandidatSuivi;
import com.soat.planification_entretien.domain.planification.EmailService;
import com.soat.planification_entretien.domain.planification.Entretien;
import com.soat.planification_entretien.domain.planification.EntretienRepository;
import com.soat.planification_entretien.domain.planification.RecruteurPossible;
import com.soat.planification_entretien.domain.planification.TrouverRecruteurDisponibleService;
import com.soat.planification_entretien.domain.preparation.CandidatRepository;
import com.soat.planification_entretien.domain.preparation.RecruteurRepository;
import org.springframework.stereotype.Service;


@Service
public class PlanifierEntretien {
    private final EntretienRepository entretienRepository;
    private final EmailService emailService;
    private final CandidatRepository candidatRepository;
    private final RecruteurRepository recruteurRepository;

    public PlanifierEntretien(EntretienRepository entretienRepository,
                              CandidatRepository candidatRepository,
                              RecruteurRepository recruteurRepository,
                              EmailService emailService) {
        this.entretienRepository = entretienRepository;
        this.candidatRepository = candidatRepository;
        this.recruteurRepository = recruteurRepository;
        this.emailService = emailService;
    }

    public boolean execute(Integer candidatId, LocalDateTime dateEtHeureDisponibiliteDuCandidat, LocalDateTime dateEtHeureDisponibiliteDuRecruteur) {
        try {
            var candidatOpt = candidatRepository.findById(candidatId)
                    .map(c -> new CandidatSuivi(c.getId(),
                            c.getEmail(), new Profil(c.getLanguage(), c.getExperienceInYears())));
            if (candidatOpt.isEmpty()) {
                return false;
            }
            var candidat = candidatOpt.get();

            var recruteur = new TrouverRecruteurDisponibleService().trouver(candidat, recruteurRepository.findAll().stream().map(
                            r -> new RecruteurPossible(r.getId(), r.getEmail(), new Profil(r.getLanguage(), r.getExperienceInYears()), r.estDisponible()
                            ))
                    .toList()
            ).get();
            var entretien = Entretien.create(candidat);
            entretien.planifier(recruteur, dateEtHeureDisponibiliteDuRecruteur);
            entretienRepository.save(entretien);
            emailService.envoyerUnEmailDeConfirmationAuCandidat(candidat.email(), dateEtHeureDisponibiliteDuCandidat);
            emailService.envoyerUnEmailDeConfirmationAuRecruteur(recruteur.email(), dateEtHeureDisponibiliteDuCandidat);
            return true;
        } catch (Exception e) {
            return false;
        }
    }

}

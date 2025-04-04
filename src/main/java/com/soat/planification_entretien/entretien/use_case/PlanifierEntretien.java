package com.soat.planification_entretien.entretien.use_case;

import java.time.LocalDateTime;

import com.soat.planification_entretien.common.MessageBus;
import com.soat.planification_entretien.entretien.domain.Candidat;
import com.soat.planification_entretien.entretien.domain.ConsultantRecruteur;
import com.soat.planification_entretien.entretien.domain.EmailService;
import com.soat.planification_entretien.entretien.domain.Entretien;
import com.soat.planification_entretien.entretien.domain.EntretienEvent;
import com.soat.planification_entretien.entretien.domain.EntretienPlanifié;
import com.soat.planification_entretien.entretien.domain.EntretienRepository;
import org.springframework.stereotype.Service;

@Service
public class PlanifierEntretien {
    private final EntretienRepository entretienRepository;
    private final EmailService emailService;
    private final MessageBus messageBus;

    public PlanifierEntretien(EntretienRepository entretienRepository, EmailService emailService, MessageBus messageBus) {
        this.entretienRepository = entretienRepository;
        this.emailService = emailService;
        this.messageBus = messageBus;
    }

    public boolean execute(Candidat candidat, ConsultantRecruteur recruteur, LocalDateTime dateEtHeureDisponibiliteDuCandidat, LocalDateTime dateEtHeureDisponibiliteDuRecruteur) {
        Entretien entretien = new Entretien(candidat, recruteur);
        EntretienEvent entretienEvent = entretien.planifier(dateEtHeureDisponibiliteDuCandidat, dateEtHeureDisponibiliteDuRecruteur);
        if (entretienEvent instanceof EntretienPlanifié) {
            entretienRepository.save(entretien);
            emailService.envoyerUnEmailDeConfirmationAuCandidat(candidat.email(), dateEtHeureDisponibiliteDuCandidat);
            emailService.envoyerUnEmailDeConfirmationAuRecruteur(recruteur.email(), dateEtHeureDisponibiliteDuCandidat);
            messageBus.send(entretienEvent);
            return true;
        }
        return false;
    }

}

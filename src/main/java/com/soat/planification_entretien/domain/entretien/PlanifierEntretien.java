package com.soat.planification_entretien.domain.entretien;

import java.time.LocalDateTime;

import com.soat.planification_entretien.domain.MessageBus;
import com.soat.planification_entretien.domain.candidat.Candidat;
import com.soat.planification_entretien.domain.recruteur.Recruteur;
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

    public boolean execute(Candidat candidat, Recruteur recruteur, LocalDateTime dateEtHeureDisponibiliteDuCandidat, LocalDateTime dateEtHeureDisponibiliteDuRecruteur) {
        Entretien entretien = new Entretien(candidat, recruteur);
        if (entretien.planifier(dateEtHeureDisponibiliteDuCandidat, dateEtHeureDisponibiliteDuRecruteur)) {
            int id = entretienRepository.save(entretien);
            emailService.envoyerUnEmailDeConfirmationAuCandidat(candidat.getEmail(), dateEtHeureDisponibiliteDuCandidat);
            emailService.envoyerUnEmailDeConfirmationAuRecruteur(recruteur.getEmail(), dateEtHeureDisponibiliteDuCandidat);
            messageBus.send(new EntretienCréé(id));
            return true;
        }
        return false;
    }

}

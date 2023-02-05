package com.soat.planification_entretien.entretien.application_service;

import java.time.LocalDateTime;

import com.soat.planification_entretien.common.application_service.MessageBus;
import com.soat.planification_entretien.entretien.domain.Candidat;
import com.soat.planification_entretien.entretien.domain.EmailService;
import com.soat.planification_entretien.entretien.domain.Entretien;
import com.soat.planification_entretien.entretien.domain.EntretienCréé;
import com.soat.planification_entretien.entretien.domain.EntretienRepository;
import com.soat.planification_entretien.entretien.domain.Recruteur;
import org.springframework.stereotype.Service;

@Service
public class PlanifierEntretienCommandHandler {
    private final EntretienRepository entretienRepository;
    private final EmailService emailService;
    private final MessageBus messageBus;

    public PlanifierEntretienCommandHandler(EntretienRepository entretienRepository, EmailService emailService, MessageBus messageBus) {
        this.entretienRepository = entretienRepository;
        this.emailService = emailService;
        this.messageBus = messageBus;
    }

    public boolean execute(Candidat candidat, Recruteur recruteur, LocalDateTime dateEtHeureDisponibiliteDuCandidat, LocalDateTime dateEtHeureDisponibiliteDuRecruteur) {
        Entretien entretien = new Entretien(candidat, recruteur);
        if (entretien.planifier(dateEtHeureDisponibiliteDuCandidat, dateEtHeureDisponibiliteDuRecruteur) instanceof EntretienCréé entretienCréé) {
            entretienRepository.save(entretien);
            emailService.envoyerUnEmailDeConfirmationAuCandidat(candidat.adresseEmail(), dateEtHeureDisponibiliteDuCandidat);
            emailService.envoyerUnEmailDeConfirmationAuRecruteur(recruteur.adresseEmail(), dateEtHeureDisponibiliteDuCandidat);
            messageBus.send(entretienCréé);
            return true;
        }
        return false;
    }

}

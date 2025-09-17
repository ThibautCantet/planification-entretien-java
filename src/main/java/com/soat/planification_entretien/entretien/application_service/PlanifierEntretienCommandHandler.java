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

    public boolean handle(PlanifierEntretienCommand command) {
        Entretien entretien = new Entretien(command.candidat(), command.recruteur());
        if (entretien.planifier(command.dateEtHeureDisponibiliteDuCandidat(), command.dateEtHeureDisponibiliteDuRecruteur()) instanceof EntretienCréé entretienCréé) {
            entretienRepository.save(entretien);
            emailService.envoyerUnEmailDeConfirmationAuCandidat(command.candidat().adresseEmail(), command.dateEtHeureDisponibiliteDuCandidat());
            emailService.envoyerUnEmailDeConfirmationAuRecruteur(command.recruteur().adresseEmail(), command.dateEtHeureDisponibiliteDuCandidat());
            messageBus.send(entretienCréé);
            return true;
        }
        return false;
    }


    public record PlanifierEntretienCommand(Candidat candidat, Recruteur recruteur,
                                            LocalDateTime dateEtHeureDisponibiliteDuCandidat,
                                            LocalDateTime dateEtHeureDisponibiliteDuRecruteur) {
    }
}
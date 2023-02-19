package com.soat.planification_entretien.entretien.command;

import com.soat.planification_entretien.common.application_service.MessageBus;
import com.soat.planification_entretien.entretien.domain.EmailService;
import com.soat.planification_entretien.entretien.domain.Entretien;
import com.soat.planification_entretien.entretien.domain.EntretienCréé;
import com.soat.planification_entretien.entretien.domain.EntretienRepository;
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

    public boolean handle(PlanifierEntretienCommand planifierEntretienCommand) {
        Entretien entretien = new Entretien(planifierEntretienCommand.candidat(), planifierEntretienCommand.recruteur());
        if (entretien.planifier(planifierEntretienCommand.dateEtHeureDisponibiliteDuCandidat(), planifierEntretienCommand.dateEtHeureDisponibiliteDuRecruteur()) instanceof EntretienCréé entretienCréé) {
            entretienRepository.save(entretien);
            emailService.envoyerUnEmailDeConfirmationAuCandidat(planifierEntretienCommand.candidat().adresseEmail(), planifierEntretienCommand.dateEtHeureDisponibiliteDuCandidat());
            emailService.envoyerUnEmailDeConfirmationAuRecruteur(planifierEntretienCommand.recruteur().adresseEmail(), planifierEntretienCommand.dateEtHeureDisponibiliteDuCandidat());
            messageBus.send(entretienCréé);
            return true;
        }
        return false;
    }

}

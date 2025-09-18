package com.soat.planification_entretien.entretien.command.application_service;

import java.time.LocalDateTime;

import com.soat.planification_entretien.common.application_service.MessageBus;
import com.soat.planification_entretien.common.cqrs.command.Command;
import com.soat.planification_entretien.common.cqrs.command.CommandHandler;
import com.soat.planification_entretien.common.cqrs.command.CommandResponse;
import com.soat.planification_entretien.common.cqrs.event.Event;
import com.soat.planification_entretien.entretien.command.domain.event.EntretienCréé;
import com.soat.planification_entretien.entretien.command.domain.event.EntretienNonPlanifie;
import com.soat.planification_entretien.entretien.command.domain.event.EntretienPlanifie;
import com.soat.planification_entretien.entretien.command.domain.model.Candidat;
import com.soat.planification_entretien.entretien.command.domain.model.Entretien;
import com.soat.planification_entretien.entretien.command.domain.model.Recruteur;
import com.soat.planification_entretien.entretien.command.domain.port.repository.EntretienRepository;
import com.soat.planification_entretien.entretien.query.domain.port.service.EmailService;

public class PlanifierEntretienCommandHandler implements CommandHandler<PlanifierEntretienCommandHandler.PlanifierEntretienCommand, CommandResponse<Event>> {
    private final EntretienRepository entretienRepository;
    private final EmailService emailService;
    private final MessageBus messageBus;

    public PlanifierEntretienCommandHandler(EntretienRepository entretienRepository, EmailService emailService, MessageBus messageBus) {
        this.entretienRepository = entretienRepository;
        this.emailService = emailService;
        this.messageBus = messageBus;
    }

    public CommandResponse<Event> handle(PlanifierEntretienCommand command) {
        Entretien entretien = new Entretien(command.candidat(), command.recruteur());
        if (entretien.planifier(command.dateEtHeureDisponibiliteDuCandidat(), command.dateEtHeureDisponibiliteDuRecruteur()) instanceof EntretienCréé entretienCréé) {
            entretienRepository.save(entretien);
            emailService.envoyerUnEmailDeConfirmationAuCandidat(command.candidat().adresseEmail(), command.dateEtHeureDisponibiliteDuCandidat());
            emailService.envoyerUnEmailDeConfirmationAuRecruteur(command.recruteur().adresseEmail(), command.dateEtHeureDisponibiliteDuCandidat());
            messageBus.send(entretienCréé);
            return new CommandResponse<>(new EntretienPlanifie(entretien.getId()));
        }
        return new CommandResponse<>(new EntretienNonPlanifie(entretien.getId()));
    }

    @Override
    public Class listenTo() {
        return PlanifierEntretienCommand.class;
    }

    public record PlanifierEntretienCommand(Candidat candidat, Recruteur recruteur,
                                            LocalDateTime dateEtHeureDisponibiliteDuCandidat,
                                            LocalDateTime dateEtHeureDisponibiliteDuRecruteur) implements Command {
    }
}

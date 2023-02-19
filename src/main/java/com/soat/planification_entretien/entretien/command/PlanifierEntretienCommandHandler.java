package com.soat.planification_entretien.entretien.command;

import com.soat.planification_entretien.common.application_service.MessageBus;
import com.soat.planification_entretien.common.cqrs.command.CommandHandler;
import com.soat.planification_entretien.common.cqrs.command.CommandResponse;
import com.soat.planification_entretien.common.cqrs.event.Event;
import com.soat.planification_entretien.entretien.command.domain.EmailService;
import com.soat.planification_entretien.entretien.command.domain.Entretien;
import com.soat.planification_entretien.entretien.command.domain.EntretienCréé;
import com.soat.planification_entretien.entretien.command.domain.EntretienRepository;

public class PlanifierEntretienCommandHandler implements CommandHandler<PlanifierEntretienCommand, CommandResponse<Event>> {
    private final EntretienRepository entretienRepository;
    private final EmailService emailService;
    private final MessageBus messageBus;

    public PlanifierEntretienCommandHandler(EntretienRepository entretienRepository, EmailService emailService, MessageBus messageBus) {
        this.entretienRepository = entretienRepository;
        this.emailService = emailService;
        this.messageBus = messageBus;
    }

    @Override
    public CommandResponse<Event> handle(PlanifierEntretienCommand planifierEntretienCommand) {
        Entretien entretien = new Entretien(planifierEntretienCommand.candidat(), planifierEntretienCommand.recruteur());
        Event event = entretien.planifier(planifierEntretienCommand.dateEtHeureDisponibiliteDuCandidat(), planifierEntretienCommand.dateEtHeureDisponibiliteDuRecruteur());
        if (event instanceof EntretienCréé entretienCréé) {
            entretienRepository.save(entretien);
            emailService.envoyerUnEmailDeConfirmationAuCandidat(planifierEntretienCommand.candidat().adresseEmail(), planifierEntretienCommand.dateEtHeureDisponibiliteDuCandidat());
            emailService.envoyerUnEmailDeConfirmationAuRecruteur(planifierEntretienCommand.recruteur().adresseEmail(), planifierEntretienCommand.dateEtHeureDisponibiliteDuCandidat());
            messageBus.send(entretienCréé);
            return new CommandResponse<>(entretienCréé);
        }
        return new CommandResponse<>(event);
    }

    @Override
    public Class listenTo() {
        return PlanifierEntretienCommand.class;
    }

}

package com.soat.planification_entretien.recruteur.command.application_service;

import java.util.List;

import com.soat.planification_entretien.common.application_service.MessageBus;
import com.soat.planification_entretien.common.cqrs.command.Command;
import com.soat.planification_entretien.common.cqrs.command.CommandHandler;
import com.soat.planification_entretien.common.cqrs.command.CommandResponse;
import com.soat.planification_entretien.common.cqrs.event.Event;
import com.soat.planification_entretien.recruteur.command.domain.event.RecruteurCree;
import com.soat.planification_entretien.recruteur.command.domain.model.Recruteur;
import com.soat.planification_entretien.recruteur.command.domain.port.repository.RecruteurRepository;

public class CreerRecruteurCommandHandler implements CommandHandler<CreerRecruteurCommandHandler.CreerRecruteurCommand, CommandResponse<Event>> {

    private final RecruteurRepository recruteurRepository;
    private final MessageBus messageBus;

    public CreerRecruteurCommandHandler(RecruteurRepository recruteurRepository, MessageBus messageBus) {
        this.recruteurRepository = recruteurRepository;
        this.messageBus = messageBus;
    }

    public CommandResponse<Event> handle(CreerRecruteurCommand command) {
        try {
            Recruteur recruteur = new Recruteur(command.language(), command.email(), Integer.parseInt(command.experienceEnAnnees()));
            Recruteur savedRecruteur = recruteurRepository.save(recruteur);

            var recruteurCree = new RecruteurCree(savedRecruteur.getId(), recruteur.getLanguage(), recruteur.getExperienceInYears(), recruteur.getAdresseEmail());
            messageBus.send(recruteurCree);

            return new CommandResponse<>(recruteurCree);
        } catch (IllegalArgumentException e) {
            return new CommandResponse<>(List.of());
        }
    }

    @Override
    public Class listenTo() {
        return CreerRecruteurCommandHandler.CreerRecruteurCommand.class;
    }

    public record CreerRecruteurCommand(String language, String email, String experienceEnAnnees) implements Command {
    }
}

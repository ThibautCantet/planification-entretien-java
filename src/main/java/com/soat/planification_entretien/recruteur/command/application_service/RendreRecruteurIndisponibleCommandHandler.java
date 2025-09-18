package com.soat.planification_entretien.recruteur.command.application_service;

import java.util.List;

import com.soat.planification_entretien.common.cqrs.command.Command;
import com.soat.planification_entretien.common.cqrs.command.CommandHandler;
import com.soat.planification_entretien.common.cqrs.command.CommandResponse;
import com.soat.planification_entretien.common.cqrs.event.Event;
import com.soat.planification_entretien.recruteur.command.domain.port.repository.RecruteurRepository;

public class RendreRecruteurIndisponibleCommandHandler implements CommandHandler<RendreRecruteurIndisponibleCommandHandler.RendreRecruteurIndisponibleCommand, CommandResponse<Event>> {

    private final RecruteurRepository recruteurRepository;

    public RendreRecruteurIndisponibleCommandHandler(RecruteurRepository recruteurRepository) {
        this.recruteurRepository = recruteurRepository;
    }

    public CommandResponse<Event> handle(RendreRecruteurIndisponibleCommand command) {
        var maybeRecruteur = recruteurRepository.findById(command.recruteurId());
        maybeRecruteur.ifPresent(recruteur -> {
            recruteur.rendreIndisponible();
            recruteurRepository.save(recruteur);
        });

        return new CommandResponse(List.of());
    }

    @Override
    public Class listenTo() {
        return RendreRecruteurIndisponibleCommandHandler.RendreRecruteurIndisponibleCommand.class;
    }

    public record RendreRecruteurIndisponibleCommand(Integer recruteurId) implements Command {
    }
}

package com.soat.planification_entretien.entretien.command.application_service;

import java.util.Optional;

import com.soat.planification_entretien.common.cqrs.command.Command;
import com.soat.planification_entretien.common.cqrs.command.CommandHandler;
import com.soat.planification_entretien.common.cqrs.command.CommandResponse;
import com.soat.planification_entretien.common.cqrs.event.Event;
import com.soat.planification_entretien.entretien.command.domain.event.EntretienNonTrouvé;
import com.soat.planification_entretien.entretien.command.domain.event.EntretienValidé;
import com.soat.planification_entretien.entretien.command.domain.model.Entretien;
import com.soat.planification_entretien.entretien.command.domain.port.repository.EntretienRepository;

public class ValiderEntretienCommandHandler implements CommandHandler<ValiderEntretienCommandHandler.ValiderEntretienCommand, CommandResponse<Event>> {
    private final EntretienRepository entretienRepository;

    public ValiderEntretienCommandHandler(EntretienRepository entretienRepository) {
        this.entretienRepository = entretienRepository;
    }

    public CommandResponse<Event> handle(ValiderEntretienCommand command) {
        Optional<Entretien> maybeEntretien = entretienRepository.findById(command.entretienId());

        Event event = new EntretienNonTrouvé(command.entretienId());
        if (maybeEntretien.isPresent()) {
            var entretien = maybeEntretien.get();
            entretien.valider();
            entretienRepository.save(entretien);
            event = new EntretienValidé(command.entretienId());
        }
        return new CommandResponse<>(event);
    }

    @Override
    public Class listenTo() {
        return ValiderEntretienCommand.class;
    }

    public record ValiderEntretienCommand(int entretienId) implements Command {
    }

}

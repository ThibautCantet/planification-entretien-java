package com.soat.planification_entretien.entretien.command;

import java.util.List;
import java.util.Optional;
import java.util.concurrent.atomic.AtomicReference;

import com.soat.planification_entretien.common.cqrs.command.CommandHandler;
import com.soat.planification_entretien.common.cqrs.command.CommandResponse;
import com.soat.planification_entretien.common.cqrs.event.Event;
import com.soat.planification_entretien.entretien.command.domain.Entretien;
import com.soat.planification_entretien.entretien.command.domain.EntretienRepository;
import com.soat.planification_entretien.entretien.command.domain.NonEntretienValidé;

public class ValiderEntretienCommandHandler implements CommandHandler<ValiderEntretienCommand, CommandResponse<Event>> {
    private final EntretienRepository entretienRepository;

    public ValiderEntretienCommandHandler(EntretienRepository entretienRepository) {
        this.entretienRepository = entretienRepository;
    }

    @Override
    public CommandResponse<Event> handle(ValiderEntretienCommand validerEntretienCommand) {
        Optional<Entretien> maybeEntretien = entretienRepository.findById(validerEntretienCommand.entretienId());

        AtomicReference<Event> event = new AtomicReference<>(new NonEntretienValidé());
        maybeEntretien.ifPresent(entretien -> {
            event.set(entretien.valider());
            entretienRepository.save(entretien);
        });

        return new CommandResponse<>(List.of(event.get()));
    }

    @Override
    public Class listenTo() {
        return ValiderEntretienCommand.class;
    }
}

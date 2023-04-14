package com.soat.planification_entretien.entretien.command;

import java.util.List;
import java.util.Optional;
import java.util.concurrent.atomic.AtomicReference;

import com.soat.planification_entretien.common.cqrs.command.CommandHandler;
import com.soat.planification_entretien.common.cqrs.command.CommandResponse;
import com.soat.planification_entretien.common.cqrs.event.Event;
import com.soat.planification_entretien.entretien.command.domain.Entretien;
import com.soat.planification_entretien.entretien.command.domain.EntretienAnnulé;
import com.soat.planification_entretien.entretien.command.domain.EntretienRepository;
import com.soat.planification_entretien.entretien.command.domain.NonEntretienValidé;

public class AnnulerEntretienCommandHandler implements CommandHandler<AnnulerEntretienCommand, CommandResponse<Event>> {
    private final EntretienRepository entretienRepository;

    public AnnulerEntretienCommandHandler(EntretienRepository entretienRepository) {
        this.entretienRepository = entretienRepository;
    }

    @Override
    public CommandResponse<Event> handle(AnnulerEntretienCommand annulerEntretienCommand) {
        Optional<Entretien> maybeEntretien = entretienRepository.findById(annulerEntretienCommand.entretienId());

        AtomicReference<Event> event = new AtomicReference<>(new NonEntretienValidé());
        maybeEntretien.ifPresent(entretien -> {
            event.set(entretien.annuler());
            entretienRepository.save(entretien);
        });

        return new CommandResponse<>(new EntretienAnnulé(annulerEntretienCommand.entretienId()));
    }

    @Override
    public Class listenTo() {
        return AnnulerEntretienCommand.class;
    }
}

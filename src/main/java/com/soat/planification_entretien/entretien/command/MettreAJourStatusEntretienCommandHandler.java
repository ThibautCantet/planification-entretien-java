package com.soat.planification_entretien.entretien.command;

import com.soat.planification_entretien.cqrs.CommandHandler;
import com.soat.planification_entretien.cqrs.CommandResponse;
import com.soat.planification_entretien.cqrs.Event;
import com.soat.planification_entretien.entretien.command.domain.entity.Entretien;
import com.soat.planification_entretien.entretien.command.domain.repository.EntretienRepository;
import com.soat.planification_entretien.entretien.event.EntretienMisAJour;
import com.soat.planification_entretien.entretien.event.EntretienNonTrouve;

public class MettreAJourStatusEntretienCommandHandler implements CommandHandler<MettreAJourStatusEntretienCommand, CommandResponse<Void, Event>> {
    private final EntretienRepository entretienRepository;

    public MettreAJourStatusEntretienCommandHandler(EntretienRepository entretienRepository) {
        this.entretienRepository = entretienRepository;
    }

    @Override
    public CommandResponse<Void, Event> handle(MettreAJourStatusEntretienCommand command) {
        Entretien entretien = entretienRepository.findById(command.id());

        if (entretien == null) {
            return new CommandResponse<>(new EntretienNonTrouve());
        }

        switch (command.status()) {
            case CONFIRME -> entretien.confirmer();
            case ANNULE -> entretien.annuler();
        }

        entretienRepository.save(entretien);

        return new CommandResponse<>(new EntretienMisAJour(entretien.getId(), entretien.getStatus()));
    }

    @Override
    public Class listenTo() {
        return MettreAJourStatusEntretienCommand.class;
    }
}

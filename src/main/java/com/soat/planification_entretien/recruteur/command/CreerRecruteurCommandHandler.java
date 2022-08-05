package com.soat.planification_entretien.recruteur.command;

import java.util.UUID;

import com.soat.planification_entretien.cqrs.CommandHandler;
import com.soat.planification_entretien.cqrs.CommandResponse;
import com.soat.planification_entretien.cqrs.Event;
import com.soat.planification_entretien.recruteur.command.domain.entity.Recruteur;
import com.soat.planification_entretien.recruteur.command.domain.repository.RecruteurRepository;
import com.soat.planification_entretien.recruteur.event.RecruteurCree;
import com.soat.planification_entretien.recruteur.event.RecruteurNonCree;

public class CreerRecruteurCommandHandler implements CommandHandler<CreerRecruteurCommand, CommandResponse<Event>> {

    private final RecruteurRepository recruteurRepository;

    public CreerRecruteurCommandHandler(RecruteurRepository recruteurRepository) {
        this.recruteurRepository = recruteurRepository;
    }

    @Override
    public CommandResponse<Event> handle(CreerRecruteurCommand command) {
        UUID id = recruteurRepository.next();
        Recruteur recruteur = Recruteur.create(id.toString(), command.language(), command.email(), command.experienceEnAnnees());
        if (recruteur.getEvent() instanceof RecruteurNonCree) {
            return new CommandResponse<>(recruteur.getEvent());
        }

        Recruteur savedRecruteur = recruteurRepository.save(recruteur);

        return new CommandResponse<>(new RecruteurCree(savedRecruteur.getId()));
    }

    @Override
    public Class listenTo() {
        return CreerRecruteurCommand.class;
    }
}

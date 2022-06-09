package com.soat.planification_entretien.domain.recruteur.command;

import java.util.UUID;

import com.soat.planification_entretien.cqrs.CommandHandler;
import com.soat.planification_entretien.cqrs.CommandResponse;
import com.soat.planification_entretien.cqrs.Event;
import com.soat.planification_entretien.domain.recruteur.command.entity.Recruteur;
import com.soat.planification_entretien.domain.recruteur.command.repository.RecruteurRepository;
import com.soat.planification_entretien.domain.recruteur.event.RecruteurCree;
import com.soat.planification_entretien.domain.recruteur.event.RecruteurNonCree;

public class CreerRecruteurCommandHandler implements CommandHandler<CreerRecruteurCommand, CommandResponse<String, Event>> {

    private final RecruteurRepository recruteurRepository;

    public CreerRecruteurCommandHandler(RecruteurRepository recruteurRepository) {
        this.recruteurRepository = recruteurRepository;
    }

    @Override
    public CommandResponse<String, Event> handle(CreerRecruteurCommand command) {
        UUID id = recruteurRepository.next();
        Recruteur recruteur = Recruteur.create(id.toString(), command.language(), command.email(), command.experienceEnAnnees());
        if (recruteur.getEvent() instanceof RecruteurNonCree) {
            return new CommandResponse<>(recruteur.getEvent());
        }

        Recruteur savedRecruteur = recruteurRepository.save(recruteur);

        return new CommandResponse<>(savedRecruteur.getId(), new RecruteurCree());
    }

    @Override
    public Class listenTo() {
        return CreerRecruteurCommand.class;
    }
}

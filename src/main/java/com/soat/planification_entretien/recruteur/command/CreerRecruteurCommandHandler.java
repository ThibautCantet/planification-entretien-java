package com.soat.planification_entretien.recruteur.command;

import com.soat.planification_entretien.common.cqrs.command.CommandHandler;
import com.soat.planification_entretien.common.cqrs.command.CommandResponse;
import com.soat.planification_entretien.common.cqrs.event.Event;
import com.soat.planification_entretien.recruteur.command.domain.Recruteur;
import com.soat.planification_entretien.recruteur.command.domain.RecruteurCrée;
import com.soat.planification_entretien.recruteur.command.domain.RecruteurNonCrée;
import com.soat.planification_entretien.recruteur.command.domain.RecruteurRepository;

public class CreerRecruteurCommandHandler implements CommandHandler<CreerRecruteurCommand, CommandResponse<Event>> {

    private final RecruteurRepository recruteurRepository;

    public CreerRecruteurCommandHandler(RecruteurRepository recruteurRepository) {
        this.recruteurRepository = recruteurRepository;
    }

    @Override
    public CommandResponse<Event> handle(CreerRecruteurCommand creerRecruteurCommand) {
        try {
            Recruteur recruteur = new Recruteur(creerRecruteurCommand.language(), creerRecruteurCommand.email(), Integer.parseInt(creerRecruteurCommand.experienceEnAnnees()));
            Recruteur savedRecruteur = recruteurRepository.save(recruteur);

            RecruteurCrée recruteurCrée = new RecruteurCrée(savedRecruteur.getId(),
                    savedRecruteur.getLanguage(),
                    savedRecruteur.getExperienceInYears(),
                    savedRecruteur.getAdresseEmail()
            );

            return new CommandResponse<>(recruteurCrée);
        } catch (IllegalArgumentException e) {
            return new CommandResponse<>(new RecruteurNonCrée());
        }
    }

    @Override
    public Class listenTo() {
        return CreerRecruteurCommand.class;
    }
}

package com.soat.planification_entretien.domain.recruteur.command;

import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import com.soat.planification_entretien.cqrs.CommandHandler;
import com.soat.planification_entretien.cqrs.CommandResponse;
import com.soat.planification_entretien.cqrs.Event;
import com.soat.planification_entretien.domain.recruteur.command.entity.Recruteur;
import com.soat.planification_entretien.domain.recruteur.event.RecruteurCree;
import com.soat.planification_entretien.domain.recruteur.event.RecruteurNonCree;
import com.soat.planification_entretien.domain.recruteur.command.repository.RecruteurRepository;

public class CreerRecruteurCommandHandler implements CommandHandler<CreerRecruteurCommand, CommandResponse<Integer, Event>> {
    private static final String EMAIL_REGEX = "^[\\w-_.+]*[\\w-_.]@([\\w]+\\.)+[\\w]+[\\w]$";

    private final RecruteurRepository recruteurRepository;

    public CreerRecruteurCommandHandler(RecruteurRepository recruteurRepository) {
        this.recruteurRepository = recruteurRepository;
    }

    @Override
    public CommandResponse<Integer, Event> handle(CreerRecruteurCommand command) {
        if (command.language().isBlank() || !isEmail(command.email()) || command.experienceEnAnnees().isBlank() || Integer.parseInt(command.experienceEnAnnees()) < 0) {
            return new CommandResponse<>(new RecruteurNonCree());
        }

        Recruteur recruteur = new Recruteur(command.language(), command.email(), Integer.parseInt(command.experienceEnAnnees()));
        Recruteur savedRecruteur = recruteurRepository.save(recruteur);

        return new CommandResponse<>(savedRecruteur.getId(), new RecruteurCree());
    }

    private static boolean isEmail(String adresse) {
        final Pattern r = Pattern.compile(EMAIL_REGEX);
        final Matcher m = r.matcher(adresse);
        return m.matches();
    }

    @Override
    public Class listenTo() {
        return CreerRecruteurCommand.class;
    }
}

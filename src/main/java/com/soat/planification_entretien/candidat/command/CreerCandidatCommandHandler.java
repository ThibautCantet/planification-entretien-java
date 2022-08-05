package com.soat.planification_entretien.candidat.command;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import com.soat.planification_entretien.cqrs.CommandHandler;
import com.soat.planification_entretien.cqrs.CommandResponse;
import com.soat.planification_entretien.cqrs.Event;
import com.soat.planification_entretien.candidat.command.repository.CandidatRepository;
import com.soat.planification_entretien.candidat.command.domain.entity.Candidat;
import com.soat.planification_entretien.candidat.event.CandidatCree;
import com.soat.planification_entretien.candidat.event.CandidatNonCree;

public class CreerCandidatCommandHandler implements CommandHandler<CreerCandidatCommand, CommandResponse<Event>> {
    private static final String EMAIL_REGEX = "^[\\w-_.+]*[\\w-_.]@([\\w]+\\.)+[\\w]+[\\w]$";

    private final CandidatRepository candidatRepository;

    public CreerCandidatCommandHandler(CandidatRepository candidatRepository) {
        this.candidatRepository = candidatRepository;
    }

    public CommandResponse<Event> handle(CreerCandidatCommand command) {
        if (command.language().isBlank() || !isEmail(command.email()) || command.experienceEnAnnees().isBlank() || Integer.parseInt(command.experienceEnAnnees()) < 0) {
            return new CommandResponse<>(new CandidatNonCree());
        }

        Candidat candidat = new Candidat(command.language(), command.email(), Integer.parseInt(command.experienceEnAnnees()));
        Candidat savedCandidat = candidatRepository.save(candidat);

        return new CommandResponse<>(new CandidatCree(savedCandidat.getId()));
    }

    private static boolean isEmail(String adresse) {
        final Pattern r = Pattern.compile(EMAIL_REGEX);
        final Matcher m = r.matcher(adresse);
        return m.matches();
    }

    @Override
    public Class listenTo() {
        return CreerCandidatCommand.class;
    }
}

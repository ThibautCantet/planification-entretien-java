package com.soat.planification_entretien.domain.candidat;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import com.soat.planification_entretien.cqrs.CommandHandler;
import com.soat.planification_entretien.cqrs.CommandResponse;
import com.soat.planification_entretien.cqrs.Event;

public class CreerCandidatCommandHandler implements CommandHandler<CreerCandidatCommand, CommandResponse<Integer, Event>> {
    private static final String EMAIL_REGEX = "^[\\w-_.+]*[\\w-_.]@([\\w]+\\.)+[\\w]+[\\w]$";

    private final CandidatRepository candidatRepository;

    public CreerCandidatCommandHandler(CandidatRepository candidatRepository) {
        this.candidatRepository = candidatRepository;
    }

    public CommandResponse<Integer, Event> handle(CreerCandidatCommand command) {
        if (command.language().isBlank() || !isEmail(command.email()) || command.experienceEnAnnees().isBlank() || Integer.parseInt(command.experienceEnAnnees()) < 0) {
            return new CommandResponse<>(new CandidatNonCree());
        }

        Candidat candidat = new Candidat(command.language(), command.email(), Integer.parseInt(command.experienceEnAnnees()));
        Candidat savedCandidat = candidatRepository.save(candidat);

        return new CommandResponse<>(savedCandidat.getId(), new CandidatCree());
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

package com.soat.planification_entretien.candidat.command;

import java.util.List;

import com.soat.planification_entretien.candidat.command.domain.Candidat;
import com.soat.planification_entretien.candidat.command.domain.CandidatCrée;
import com.soat.planification_entretien.candidat.command.domain.CandidatRepository;
import com.soat.planification_entretien.candidat.command.domain_service.CandidatFactory;
import com.soat.planification_entretien.common.cqrs.command.CommandHandler;
import com.soat.planification_entretien.common.cqrs.command.CommandResponse;
import com.soat.planification_entretien.common.cqrs.event.Event;
import com.soat.planification_entretien.common.domain_service.Result;

public class CreerCandidatCommandHandler implements CommandHandler<CreerCandidatCommand, CommandResponse<Event>> {

    private final CandidatRepository candidatRepository;
    private final CandidatFactory candidatFactory;

    public CreerCandidatCommandHandler(CandidatRepository candidatRepository, CandidatFactory candidatFactory) {
        this.candidatRepository = candidatRepository;
        this.candidatFactory = candidatFactory;
    }

    @Override
    public CommandResponse handle(CreerCandidatCommand creerCandidatCommand) {
        var candidatId = candidatRepository.next();
        Result<Event, Candidat> eventCandidatResult = candidatFactory.create(candidatId, creerCandidatCommand.language(), creerCandidatCommand.email(), creerCandidatCommand.experienceEnAnnees());

        if (eventCandidatResult.event() instanceof CandidatCrée) {
            candidatRepository.save(eventCandidatResult.value());
        }

        return new CommandResponse(List.of(eventCandidatResult.event()));
    }

    @Override
    public Class listenTo() {
        return CreerCandidatCommand.class;
    }
}

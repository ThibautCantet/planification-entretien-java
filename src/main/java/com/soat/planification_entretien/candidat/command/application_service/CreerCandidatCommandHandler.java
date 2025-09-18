package com.soat.planification_entretien.candidat.command.application_service;

import com.soat.planification_entretien.candidat.command.application_service.event.CandidatNonSauvegardé;
import com.soat.planification_entretien.candidat.command.domain.event.CandidatCrée;
import com.soat.planification_entretien.candidat.command.domain.model.Candidat;
import com.soat.planification_entretien.candidat.command.domain.port.repository.CandidatRepository;
import com.soat.planification_entretien.candidat.command.domain_service.CandidatFactory;
import com.soat.planification_entretien.common.cqrs.command.Command;
import com.soat.planification_entretien.common.cqrs.command.CommandHandler;
import com.soat.planification_entretien.common.cqrs.command.CommandResponse;
import com.soat.planification_entretien.common.cqrs.event.Event;
import com.soat.planification_entretien.common.domain_service.Result;

public class CreerCandidatCommandHandler implements CommandHandler<CreerCandidatCommandHandler.CreerCandidatCommand, CommandResponse<Event>> {

    private final CandidatRepository candidatRepository;
    private final CandidatFactory candidatFactory;

    public CreerCandidatCommandHandler(CandidatRepository candidatRepository, CandidatFactory candidatFactory) {
        this.candidatRepository = candidatRepository;
        this.candidatFactory = candidatFactory;
    }

    public CommandResponse<Event> handle(CreerCandidatCommand command) {
        var candidatId = candidatRepository.next();
        Result<Event, Candidat> eventCandidatResult = candidatFactory.create(candidatId, command.language(), command.email(), command.experienceEnAnnees());

        if (eventCandidatResult.event() instanceof CandidatCrée) {
            try {
                candidatRepository.save(eventCandidatResult.value());
            } catch (Exception e) {
                return new CommandResponse<>(new CandidatNonSauvegardé());
            }
        }

        return new CommandResponse<>(eventCandidatResult.event());
    }

    @Override
    public Class listenTo() {
        return CreerCandidatCommand.class;
    }

    public record CreerCandidatCommand(String language, String email, String experienceEnAnnees) implements Command {
    }
}

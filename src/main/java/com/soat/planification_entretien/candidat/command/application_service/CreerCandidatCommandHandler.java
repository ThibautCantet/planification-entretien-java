package com.soat.planification_entretien.candidat.command.application_service;

import java.util.List;

import com.soat.planification_entretien.candidat.command.application_service.event.CandidatNonSauvegardé;
import com.soat.planification_entretien.common.domain.Event;
import com.soat.planification_entretien.candidat.command.domain.model.Candidat;
import com.soat.planification_entretien.candidat.command.domain.event.CandidatCrée;
import com.soat.planification_entretien.candidat.command.domain.port.repository.CandidatRepository;
import com.soat.planification_entretien.candidat.command.domain_service.CandidatFactory;
import com.soat.planification_entretien.common.domain_service.Result;
import org.springframework.stereotype.Service;

@Service
public class CreerCandidatCommandHandler {

    private final CandidatRepository candidatRepository;
    private final CandidatFactory candidatFactory;

    public CreerCandidatCommandHandler(CandidatRepository candidatRepository, CandidatFactory candidatFactory) {
        this.candidatRepository = candidatRepository;
        this.candidatFactory = candidatFactory;
    }

    public List<Event> handle(CreerCandidatCommand command) {
        var candidatId = candidatRepository.next();
        Result<Event, Candidat> eventCandidatResult = candidatFactory.create(candidatId, command.language(), command.email(), command.experienceEnAnnees());

        if (eventCandidatResult.event() instanceof CandidatCrée) {
            try {
                candidatRepository.save(eventCandidatResult.value());
            } catch (Exception e) {
                return List.of(new CandidatNonSauvegardé());
            }
        }

        return List.of(eventCandidatResult.event());
    }

    public record CreerCandidatCommand(String language, String email, String experienceEnAnnees) {
    }
}

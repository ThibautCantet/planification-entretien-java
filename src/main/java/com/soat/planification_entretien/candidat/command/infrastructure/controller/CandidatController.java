package com.soat.planification_entretien.candidat.command.infrastructure.controller;

import java.net.URI;

import com.soat.planification_entretien.candidat.command.application_service.event.CandidatNonSauvegardé;
import com.soat.planification_entretien.candidat.command.application_service.CreerCandidatCommandHandler;
import com.soat.planification_entretien.candidat.command.domain.event.CandidatCrée;
import com.soat.planification_entretien.common.cqrs.application.CommandController;
import com.soat.planification_entretien.common.cqrs.command.CommandResponse;
import com.soat.planification_entretien.common.cqrs.event.Event;
import com.soat.planification_entretien.common.cqrs.middleware.command.CommandBusFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import static org.springframework.http.ResponseEntity.*;

@RestController
@RequestMapping(CandidatController.PATH)
public class CandidatController extends CommandController {
    public static final String PATH = "/api/candidat/";

    public CandidatController(CommandBusFactory commandBusFactory) {
        super(commandBusFactory);
    }

    @PostMapping
    public ResponseEntity<Integer> creer(@RequestBody CandidatDto candidatDto) {
        if (validExperience(candidatDto)) {
            return badRequest().build();
        }
        CommandResponse<Event> events = getCommandBus().dispatch(new CreerCandidatCommandHandler.CreerCandidatCommand(candidatDto.language(), candidatDto.email(), candidatDto.experienceEnAnnees()));
        if (events.events().stream().noneMatch(CandidatCrée.class::isInstance)) {
            return badRequest().build();
        }

        if (events.events().stream().anyMatch(CandidatNonSauvegardé.class::isInstance)) {
            return internalServerError().build();
        }

        URI location = ServletUriComponentsBuilder.fromCurrentRequest()
                .path("/{id}")
                .buildAndExpand(events)
                .toUri();

        return created(location).body(events.findFirst(CandidatCrée.class)
                        .map(e -> (CandidatCrée) e)
                        .map(e -> e.value())
                .orElse(null));
    }

    private static boolean validExperience(CandidatDto candidatDto) {
        try {
            Integer.parseInt(candidatDto.experienceEnAnnees());
            return candidatDto.experienceEnAnnees().isBlank();
        } catch (Exception e) {
            return false;
        }
    }
}

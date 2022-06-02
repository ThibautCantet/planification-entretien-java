package com.soat.planification_entretien.application.controller;

import java.net.URI;

import com.soat.planification_entretien.cqrs.CommandResponse;
import com.soat.planification_entretien.cqrs.Event;
import com.soat.planification_entretien.domain.candidat.CandidatCree;
import com.soat.planification_entretien.domain.candidat.CreerCandidatCommand;
import com.soat.planification_entretien.infrastructure.controller.CommandController;
import com.soat.planification_entretien.infrastructure.middleware.command.CommandBusFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import static org.springframework.http.ResponseEntity.*;

@RestController
@RequestMapping(CandidatCommandController.PATH)
public class CandidatCommandController extends CommandController {
    public static final String PATH = "/api/candidat";

    public CandidatCommandController(CommandBusFactory commandBusFactory) {
        super(commandBusFactory);
        commandBusFactory.build();
    }

    @PostMapping("")
    public ResponseEntity<Integer> creer(@RequestBody CandidatDto candidatDto) {
        CommandResponse<Integer, Event> commandResponse = getCommandBus().dispatch(new CreerCandidatCommand(candidatDto.language(), candidatDto.email(), candidatDto.experienceEnAnnees()));
        if (commandResponse.event() instanceof CandidatCree) {
            return created(URI.create(PATH + "/" + commandResponse.value()))
                    .body(commandResponse.value());
        }
        return badRequest().build();
    }
}

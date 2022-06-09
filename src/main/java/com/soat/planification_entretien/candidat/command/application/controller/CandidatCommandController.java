package com.soat.planification_entretien.candidat.command.application.controller;

import java.net.URI;

import com.soat.planification_entretien.cqrs.CommandResponse;
import com.soat.planification_entretien.cqrs.Event;
import com.soat.planification_entretien.candidat.event.CandidatCree;
import com.soat.planification_entretien.candidat.command.CreerCandidatCommand;
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
        if (commandResponse.containEventType(CandidatCree.class)) {
            return created(URI.create(PATH + "/" + commandResponse.value()))
                    .body(commandResponse.value());
        }
        return badRequest().build();
    }
}
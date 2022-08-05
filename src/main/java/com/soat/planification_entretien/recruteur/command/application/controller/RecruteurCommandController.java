package com.soat.planification_entretien.recruteur.command.application.controller;

import java.net.URI;
import java.util.UUID;

import com.soat.planification_entretien.cqrs.CommandResponse;
import com.soat.planification_entretien.cqrs.Event;
import com.soat.planification_entretien.recruteur.command.CreerRecruteurCommand;
import com.soat.planification_entretien.recruteur.event.RecruteurCree;
import com.soat.planification_entretien.infrastructure.controller.CommandController;
import com.soat.planification_entretien.infrastructure.middleware.command.CommandBusFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import static org.springframework.http.ResponseEntity.*;

@RestController
@RequestMapping(RecruteurCommandController.PATH)
public class RecruteurCommandController extends CommandController {
    public static final String PATH = "/api/recruteur";

    public RecruteurCommandController(CommandBusFactory commandBusFactory) {
        super(commandBusFactory);
    }

    @PostMapping
    public ResponseEntity<UUID> creer(@RequestBody RecruteurDto recruteurDto) {
        CommandResponse<Event> commandResponse = getCommandBus().dispatch(new CreerRecruteurCommand(recruteurDto.language(), recruteurDto.email(), recruteurDto.experienceEnAnnees()));
        return commandResponse.findFirst(RecruteurCree.class)
                .map(RecruteurCree.class::cast)
                .map(event -> created(URI.create(PATH + "/" + event.id()))
                        .body(UUID.fromString(event.id())))
                .orElse(badRequest().build());
    }

}

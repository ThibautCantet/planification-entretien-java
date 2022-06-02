package com.soat.planification_entretien.application.controller;

import java.net.URI;

import com.soat.planification_entretien.cqrs.CommandResponse;
import com.soat.planification_entretien.cqrs.Event;
import com.soat.planification_entretien.domain.recruteur.CreerRecruteurCommand;
import com.soat.planification_entretien.domain.recruteur.RecruteurCree;
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

    @PostMapping("")
    public ResponseEntity<Integer> creer(@RequestBody RecruteurDto recruteurDto) {
        CommandResponse<Integer, Event> commandResponse = getCommandBus().dispatch(new CreerRecruteurCommand(recruteurDto.language(), recruteurDto.email(), recruteurDto.experienceEnAnnees()));
        if (commandResponse.event() instanceof RecruteurCree) {
            return created(URI.create(PATH + "/" + commandResponse.value())).body(commandResponse.value());
        }
        return badRequest().build();
    }

}

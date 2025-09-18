package com.soat.planification_entretien.recruteur.command.infrastructure.controller;

import com.soat.planification_entretien.common.cqrs.application.CommandController;
import com.soat.planification_entretien.common.cqrs.command.CommandResponse;
import com.soat.planification_entretien.common.cqrs.event.Event;
import com.soat.planification_entretien.common.cqrs.middleware.command.CommandBusFactory;
import com.soat.planification_entretien.recruteur.command.application_service.CreerRecruteurCommandHandler;
import com.soat.planification_entretien.recruteur.command.domain.event.RecruteurCree;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import static org.springframework.http.ResponseEntity.*;

@RestController
@RequestMapping(RecruteurCommandController.PATH)
public class RecruteurCommandController extends CommandController {

    public static final String PATH = "/api/recruteur/";

    public RecruteurCommandController(CommandBusFactory commandBusFactory) {
        super(commandBusFactory);
    }

    @PostMapping
    public ResponseEntity<Integer> creer(@RequestBody RecruteurDto recruteurDto) {
        if (validExperience(recruteurDto)) {
            return badRequest().build();
        }
        CommandResponse<Event> commandResponse = getCommandBus().dispatch(
                new CreerRecruteurCommandHandler.CreerRecruteurCommand(
                recruteurDto.language(), recruteurDto.email(), recruteurDto.experienceEnAnnees()));
        if (commandResponse.findFirst(RecruteurCree.class).isEmpty()) {
            return badRequest().build();
        }

        return created(null).body(commandResponse.findFirst(RecruteurCree.class)
                .map(RecruteurCree.class::cast)
                .map(RecruteurCree::id)
                .orElse(0));
    }

    private static boolean validExperience(RecruteurDto candidatDto) {
        try {
            Integer.parseInt(candidatDto.experienceEnAnnees());
            return candidatDto.experienceEnAnnees().isBlank();
        } catch (Exception e) {
            return false;
        }
    }

}

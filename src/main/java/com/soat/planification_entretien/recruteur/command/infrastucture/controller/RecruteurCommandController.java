package com.soat.planification_entretien.recruteur.command.infrastucture.controller;

import java.util.Optional;

import com.soat.planification_entretien.common.cqrs.application.CommandController;
import com.soat.planification_entretien.common.cqrs.command.CommandResponse;
import com.soat.planification_entretien.common.cqrs.middleware.command.CommandBusFactory;
import com.soat.planification_entretien.recruteur.command.CreerRecruteurCommand;
import com.soat.planification_entretien.recruteur.command.domain.RecruteurCrée;
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
        if (validExperience(recruteurDto)) {
            return badRequest().build();
        }

        CommandResponse commandResponse = getCommandBus().dispatch(new CreerRecruteurCommand(recruteurDto.language(), recruteurDto.email(), recruteurDto.experienceEnAnnees()));
        Optional<Integer> maybeNewId = commandResponse.findFirst(RecruteurCrée.class)
                .map(event -> ((RecruteurCrée) event).id());
        if (maybeNewId.isPresent()) {
            return created(null).body(maybeNewId.get());
        }
        return badRequest().build();

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

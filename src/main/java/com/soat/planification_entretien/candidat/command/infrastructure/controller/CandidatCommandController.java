package com.soat.planification_entretien.candidat.command.infrastructure.controller;

import java.net.URI;
import java.util.Optional;

import com.soat.planification_entretien.candidat.command.CandidatNonSauvegardé;
import com.soat.planification_entretien.candidat.command.CreerCandidatCommand;
import com.soat.planification_entretien.candidat.command.domain.CandidatCrée;
import com.soat.planification_entretien.common.cqrs.application.CommandController;
import com.soat.planification_entretien.common.cqrs.middleware.command.CommandBusFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import static org.springframework.http.ResponseEntity.*;

@RestController
@RequestMapping(CandidatCommandController.PATH)
public class CandidatCommandController extends CommandController {
    public static final String PATH = "/api/candidat";


    public CandidatCommandController(CommandBusFactory commandBusFactory) {
        super(commandBusFactory);
    }

    @PostMapping("")
    public ResponseEntity<Integer> creer(@RequestBody CandidatDto candidatDto) {
        if (validExperience(candidatDto)) {
            return badRequest().build();
        }
        var commandResponse = getCommandBus().dispatch(new CreerCandidatCommand(candidatDto.language(), candidatDto.email(), candidatDto.experienceEnAnnees()));
        if (commandResponse.findFirst(CandidatCrée.class).isEmpty()) {
            return badRequest().build();
        }

        if (commandResponse.findFirst(CandidatNonSauvegardé.class).isPresent()) {
            return internalServerError().build();
        }

        URI location = ServletUriComponentsBuilder.fromCurrentRequest()
                .path("/{id}")
                .buildAndExpand(commandResponse)
                .toUri();

        return created(location).body(((Optional<CandidatCrée>) commandResponse.findFirst(CandidatCrée.class))
                .map(CandidatCrée::value)
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

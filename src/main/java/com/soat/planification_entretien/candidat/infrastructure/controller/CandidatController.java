package com.soat.planification_entretien.candidat.infrastructure.controller;

import java.net.URI;

import com.soat.planification_entretien.candidat.command.CandidatNonSauvegardé;
import com.soat.planification_entretien.candidat.command.CreerCandidatCommand;
import com.soat.planification_entretien.candidat.command.CreerCandidatCommandHandler;
import com.soat.planification_entretien.candidat.command.domain.CandidatCrée;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import static org.springframework.http.ResponseEntity.*;

@RestController
@RequestMapping(CandidatController.PATH)
public class CandidatController {
    public static final String PATH = "/api/candidat";

    private final CreerCandidatCommandHandler creerCandidatCommandHandler;

    public CandidatController(CreerCandidatCommandHandler creerCandidatCommandHandler) {
        this.creerCandidatCommandHandler = creerCandidatCommandHandler;
    }

    @PostMapping("")
    public ResponseEntity<Integer> creer(@RequestBody CandidatDto candidatDto) {
        if (validExperience(candidatDto)) {
            return badRequest().build();
        }
        var events = creerCandidatCommandHandler.handle(new CreerCandidatCommand(candidatDto.language(), candidatDto.email(), candidatDto.experienceEnAnnees()));
        if (events.stream().noneMatch(CandidatCrée.class::isInstance)) {
            return badRequest().build();
        }

        if (events.stream().anyMatch(CandidatNonSauvegardé.class::isInstance)) {
            return internalServerError().build();
        }

        URI location = ServletUriComponentsBuilder.fromCurrentRequest()
                .path("/{id}")
                .buildAndExpand(events)
                .toUri();

        return created(location).body(events.stream()
                .filter(CandidatCrée.class::isInstance)
                .map(CandidatCrée.class::cast)
                .findFirst()
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

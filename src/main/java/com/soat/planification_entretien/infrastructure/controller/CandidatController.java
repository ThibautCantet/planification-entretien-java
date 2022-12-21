package com.soat.planification_entretien.infrastructure.controller;

import java.net.URI;

import com.soat.planification_entretien.application_service.candidat.CandidatNonSauvegardé;
import com.soat.planification_entretien.domain.candidat.CandidatCrée;
import com.soat.planification_entretien.application_service.candidat.CreerCandidat;
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

    private final CreerCandidat creerCandidat;

    public CandidatController(CreerCandidat creerCandidat) {
        this.creerCandidat = creerCandidat;
    }

    @PostMapping("")
    public ResponseEntity<Integer> creer(@RequestBody CandidatDto candidatDto) {
        if (validExperience(candidatDto)) {
            return badRequest().build();
        }
        var events = creerCandidat.execute(candidatDto.language(), candidatDto.email(), candidatDto.experienceEnAnnees());
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

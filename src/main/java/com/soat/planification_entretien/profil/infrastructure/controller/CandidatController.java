package com.soat.planification_entretien.profil.infrastructure.controller;

import java.net.URI;
import java.util.UUID;

import com.soat.planification_entretien.profil.use_case.CreerProspect;
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

    private final CreerProspect creerProspect;

    public CandidatController(CreerProspect creerProspect) {
        this.creerProspect = creerProspect;
    }

    @PostMapping("")
    public ResponseEntity<UUID> creer(@RequestBody CandidatDto candidatDto) {
        if (validExperience(candidatDto)) {
            return badRequest().build();
        }
        var createdCandidatId = creerProspect.execute(candidatDto.language(), candidatDto.email(), candidatDto.experienceEnAnnees());
        if (createdCandidatId == null) {
            return badRequest().build();
        }

        URI location = ServletUriComponentsBuilder.fromCurrentRequest()
                .path("/{id}")
                .buildAndExpand(createdCandidatId)
                .toUri();

        return created(location).body(createdCandidatId);
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

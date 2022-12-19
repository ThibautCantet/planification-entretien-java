package com.soat.planification_entretien.infrastructure.controller;

import java.net.URI;

import com.soat.planification_entretien.use_case.CreerCandidat;
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

        Integer createdCandidatId = creerCandidat.execute(candidatDto.language(), candidatDto.email(), candidatDto.experienceEnAnnees());
        if (createdCandidatId == null) {
            return badRequest().build();
        }

        URI location = ServletUriComponentsBuilder.fromCurrentRequest()
                .path("/{id}")
                .buildAndExpand(createdCandidatId)
                .toUri();

        return created(location).body(createdCandidatId);
    }
}

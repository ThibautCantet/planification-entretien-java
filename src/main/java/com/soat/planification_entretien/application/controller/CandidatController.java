package com.soat.planification_entretien.application.controller;

import com.soat.planification_entretien.domain.candidat.CreerCandidat;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

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

        return created(null).body(createdCandidatId);
    }
}

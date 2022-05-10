package com.soat.candidat.infrastructure.controller;

import com.soat.candidat.domain.Candidat;
import com.soat.candidat.use_case.CreerCandidat;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import static java.util.Optional.*;
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

        Candidat candidat = creerCandidat.execute(candidatDto.language(), candidatDto.email(), candidatDto.experienceEnAnnees());
        return ofNullable(candidat).map(c -> created(null).body(c.getId()))
                .orElse(badRequest().build());
    }
}

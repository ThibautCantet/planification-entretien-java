package com.soat.planification_entretien.controller;

import com.soat.planification_entretien.service.CandidatService;
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

    private final CandidatService candidatService;

    public CandidatController(CandidatService candidatService) {
        this.candidatService = candidatService;
    }

    @PostMapping("")
    public ResponseEntity<Integer> creer(@RequestBody CandidatDto candidatDto) {
        var resultat = candidatService.creer(
                candidatDto.language(),
                candidatDto.email(),
                candidatDto.experienceEnAnnees().isBlank() ? null : Integer.parseInt(candidatDto.experienceEnAnnees()));

        if (resultat != null) {
            return created(null).body(resultat);
        } else {
            return badRequest().build();
        }
    }
}

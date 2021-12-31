package com.soat.planification_entretien.controller;

import com.soat.planification_entretien.model.CreationCandidatRefusee;
import com.soat.planification_entretien.model.CreationCandidatValidee;
import com.soat.planification_entretien.model.ResultatCreationCandidat;
import com.soat.planification_entretien.service.CandidatService;
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

    private final CandidatService candidatService;

    public CandidatController(CandidatService candidatService) {
        this.candidatService = candidatService;
    }

    @PostMapping("")
    public ResponseEntity<ResultatCreationCandidat> creer(@RequestBody CandidatDto candidatDto) {
        ResultatCreationCandidat resultat = candidatService.creer(
                candidatDto.language(),
                candidatDto.email(),
                candidatDto.experienceEnAnnees().isBlank() ? null : Integer.parseInt(candidatDto.experienceEnAnnees()));

        if (resultat instanceof CreationCandidatValidee) {
            return created(null).body(resultat);
        } else if (resultat instanceof CreationCandidatRefusee) {
            return badRequest().body(resultat);
        }
        return internalServerError().build();
    }
}

package com.soat.planification_entretien.recruteur.query.infrastructure.controller;

import java.util.List;

import com.soat.planification_entretien.recruteur.query.ListerRecruteursExperimentesQueryHandler;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(RecruteurQueryController.PATH)
public class RecruteurQueryController {
    public static final String PATH = "/api/recruteur";

    private final ListerRecruteursExperimentesQueryHandler listerRecruteursExperimentesQueryHandler;

    public RecruteurQueryController(ListerRecruteursExperimentesQueryHandler listerRecruteursExperimentesQueryHandler) {
        this.listerRecruteursExperimentesQueryHandler = listerRecruteursExperimentesQueryHandler;
    }

    @GetMapping("")
    public ResponseEntity<List<RecruteurDetailDto>> lister() {
        List<RecruteurDetailDto> recruteurs = listerRecruteursExperimentesQueryHandler.handle().stream()
                .map(e -> new RecruteurDetailDto(e.id(), e.competence(), e.email()))
                .toList();

        return new ResponseEntity<>(recruteurs, HttpStatus.OK);
    }

}

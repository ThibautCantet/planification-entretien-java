package com.soat.planification_entretien.recruteur.query.infrastructure.controller;

import java.util.List;

import com.soat.planification_entretien.recruteur.query.application_service.ListerRecruteursExperimentesQueryHandler;
import com.soat.planification_entretien.recruteur.query.domain.model.RecruteurDetail;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(RecruteurQueryController.PATH)
public class RecruteurQueryController {
    public static final String PATH = "/api/recruteur/";

    private final ListerRecruteursExperimentesQueryHandler listerRecruteursExperimentesQueryHandler;

    public RecruteurQueryController(ListerRecruteursExperimentesQueryHandler listerRecruteursExperimentesQueryHandler) {
        this.listerRecruteursExperimentesQueryHandler = listerRecruteursExperimentesQueryHandler;
    }

    @GetMapping
    public ResponseEntity<List<RecruteurDetail>> lister() {
        List<RecruteurDetail> recruteurs = listerRecruteursExperimentesQueryHandler.handle();
        return new ResponseEntity<>(recruteurs, HttpStatus.OK);
    }

}

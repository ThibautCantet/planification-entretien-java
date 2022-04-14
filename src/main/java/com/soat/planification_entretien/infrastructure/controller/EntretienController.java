package com.soat.planification_entretien.infrastructure.controller;

import com.soat.planification_entretien.use_case.PlanifierEntretien;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import static org.springframework.http.ResponseEntity.*;

@RestController
@RequestMapping(EntretienController.PATH)
public class EntretienController {
    public static final String PATH = "/api/entretien/";

    private final PlanifierEntretien planifierEntretien;

    public EntretienController(PlanifierEntretien planifierEntretien) {
        this.planifierEntretien = planifierEntretien;
    }

    @PostMapping("planifier")
    public ResponseEntity<Void> planifier(@RequestBody EntretienDto entretienDto) {

        var planifie = planifierEntretien.execute(entretienDto.candidatId(), entretienDto.recruteurId(), entretienDto.disponibiliteDuCandidat(), entretienDto.disponibiliteDuRecruteur());

        if (planifie) {
            return created(null).build();
        } else {
            return badRequest().build();
        }

    }
}

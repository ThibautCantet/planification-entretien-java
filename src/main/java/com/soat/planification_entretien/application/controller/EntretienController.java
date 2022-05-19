package com.soat.planification_entretien.application.controller;

import java.util.List;

import com.soat.planification_entretien.domain.entretien.EntretienDetail;
import com.soat.planification_entretien.domain.entretien.ListerEntretiens;
import com.soat.planification_entretien.domain.entretien.PlanifierEntretien;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
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
    private final ListerEntretiens listerEntretiens;

    public EntretienController(PlanifierEntretien planifierEntretien, ListerEntretiens listerEntretiens) {
        this.planifierEntretien = planifierEntretien;
        this.listerEntretiens = listerEntretiens;
    }

    @GetMapping("/")
    public ResponseEntity<List<EntretienDetail>> findAll() {
        var entretiens = listerEntretiens.execute();
        return new ResponseEntity<>(entretiens, HttpStatus.OK);
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

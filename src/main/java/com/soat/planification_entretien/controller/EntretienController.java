package com.soat.planification_entretien.controller;

import com.soat.planification_entretien.model.EntretienEchouee;
import com.soat.planification_entretien.model.EntretienPlanifie;
import com.soat.planification_entretien.model.ResultatPlanificationEntretien;
import com.soat.planification_entretien.service.EntretienService;
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

    private final EntretienService entretienService;

    public EntretienController(EntretienService entretienService) {
        this.entretienService = entretienService;
    }

    @PostMapping("planifier")
    public ResponseEntity<Void> planifier(@RequestBody EntretienDto entretienDto) {

        ResultatPlanificationEntretien resultat = entretienService.planifier(entretienDto.candidatId(), entretienDto.recruteurId(), entretienDto.disponibiliteDuCandidat(), entretienDto.horaire());

        if (resultat instanceof EntretienPlanifie) {
            return created(null).build();
        } else if (resultat instanceof EntretienEchouee) {
            return badRequest().build();
        }
        return internalServerError().build();

    }
}

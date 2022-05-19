package com.soat.planification_entretien.application.controller;

import com.soat.planification_entretien.use_case.CreerRecruteur;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import static org.springframework.http.ResponseEntity.*;

@RestController
@RequestMapping(RecruteurController.PATH)
public class RecruteurController {
    public static final String PATH = "/api/recruteur";

    private final CreerRecruteur creerRecruteur;

    public RecruteurController(CreerRecruteur creerRecruteur) {
        this.creerRecruteur = creerRecruteur;
    }

    @PostMapping("")
    public ResponseEntity<Integer> creer(@RequestBody RecruteurDto recruteurDto) {
        Integer createdRecruteurId = creerRecruteur.execute(recruteurDto.language(), recruteurDto.email(), recruteurDto.experienceEnAnnees());
        if (createdRecruteurId == null) {
            return badRequest().build();
        }

        return created(null).body(createdRecruteurId);
    }

}

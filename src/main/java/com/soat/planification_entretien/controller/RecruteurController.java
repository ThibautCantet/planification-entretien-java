package com.soat.planification_entretien.controller;

import com.soat.planification_entretien.service.RecruteurService;
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

    private final RecruteurService recruteurService;

    public RecruteurController(RecruteurService recruteurService) {
        this.recruteurService = recruteurService;
    }

    @PostMapping("")
    public ResponseEntity<Integer> creer(@RequestBody RecruteurDto recruteurDto) {
        var resultat = recruteurService.creer(
                recruteurDto.language(),
                recruteurDto.email(),
                recruteurDto.experienceEnAnnees().isBlank() ? null : Integer.parseInt(recruteurDto.experienceEnAnnees()));

        if (resultat != null) {
            return created(null).body(resultat);
        } else {
            return badRequest().build();
        }

    }
}

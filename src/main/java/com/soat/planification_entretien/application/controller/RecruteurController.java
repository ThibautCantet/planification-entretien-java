package com.soat.planification_entretien.application.controller;

import java.util.List;

import com.soat.planification_entretien.domain.recruteur.CreerRecruteur;
import com.soat.planification_entretien.domain.recruteur.Lister;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
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
    private final Lister lister;

    public RecruteurController(CreerRecruteur creerRecruteur, Lister lister) {
        this.creerRecruteur = creerRecruteur;
        this.lister = lister;
    }

    @PostMapping("")
    public ResponseEntity<Integer> creer(@RequestBody RecruteurDto recruteurDto) {
        Integer createdRecruteurId = creerRecruteur.execute(recruteurDto.language(), recruteurDto.email(), recruteurDto.experienceEnAnnees());
        if (createdRecruteurId == null) {
            return badRequest().build();
        }

        return created(null).body(createdRecruteurId);
    }

    @GetMapping("")
    public ResponseEntity<List<RecruteurDetailDto>> lister() {
        List<RecruteurDetailDto> recruteurs = lister.execute().stream()
                .map(e -> new RecruteurDetailDto(e.getId(), e.getLanguage(), e.getExperienceInYears(), e.getEmail()))
                .toList();

        return new ResponseEntity<>(recruteurs, HttpStatus.OK);
    }

}

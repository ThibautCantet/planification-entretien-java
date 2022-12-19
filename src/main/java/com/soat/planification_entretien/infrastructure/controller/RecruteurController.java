package com.soat.planification_entretien.infrastructure.controller;

import java.util.List;

import com.soat.planification_entretien.use_case.CreerRecruteur;
import com.soat.planification_entretien.use_case.ListerRecruteursExperimentes;
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
    private final ListerRecruteursExperimentes listerRecruteursExperimentes;

    public RecruteurController(CreerRecruteur creerRecruteur, ListerRecruteursExperimentes listerRecruteursExperimentes) {
        this.creerRecruteur = creerRecruteur;
        this.listerRecruteursExperimentes = listerRecruteursExperimentes;
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
        List<RecruteurDetailDto> recruteurs = listerRecruteursExperimentes.execute().stream()
                .map(e -> new RecruteurDetailDto(e.getId(), e.getLanguage(), e.getExperienceInYears(), e.getEmail()))
                .toList();

        return new ResponseEntity<>(recruteurs, HttpStatus.OK);
    }

}

package com.soat.planification_entretien.infrastructure.controller;

import java.util.List;

import com.soat.planification_entretien.use_case.CreerRecruteur;
import com.soat.planification_entretien.use_case.ListerRecruteurExperimentes;
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
    private final ListerRecruteurExperimentes listerRecruteursExperimentes;

    public RecruteurController(CreerRecruteur creerRecruteur, ListerRecruteurExperimentes listerRecruteursExperimentes) {
        this.creerRecruteur = creerRecruteur;
        this.listerRecruteursExperimentes = listerRecruteursExperimentes;
    }

    @PostMapping
    public ResponseEntity<Integer> creer(@RequestBody RecruteurDto recruteurDto) {

        var savedRecruteur = creerRecruteur.execute(recruteurDto.language(), recruteurDto.email(), recruteurDto.experienceEnAnnees());

        if (savedRecruteur == null) {
            return badRequest().build();
        }

        return created(null).body(savedRecruteur.getId());
    }

    @GetMapping
    public List<RecruteurDetailDto> listerExperiments() {
        return listerRecruteursExperimentes.execute().stream()
                .map(r -> new RecruteurDetailDto(r.getId(), r.getEmail(), r.getLanguage(), r.getExperienceInYears()))
                .toList();
    }
}

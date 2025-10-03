package com.soat.planification_entretien.infrastructure.controller;

import java.util.List;

import com.soat.planification_entretien.application.use_case.CreerRecruteur;
import com.soat.planification_entretien.application.use_case.ListerRecruteursExperimentes;
import com.soat.planification_entretien.domain.model.RecruteurDetail;
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

    @PostMapping
    public ResponseEntity<Integer> creer(@RequestBody RecruteurDto recruteurDto) {

        Integer id = creerRecruteur.execute(recruteurDto.language(), recruteurDto.email(), recruteurDto.experienceEnAnnees());

        if (id == null) {
            return badRequest().build();
        }
        return created(null).body(id);
    }

    @GetMapping
    public List<RecruteurDetail> listerRecruteursExperiments() {
        return listerRecruteursExperimentes.execute();
    }
}

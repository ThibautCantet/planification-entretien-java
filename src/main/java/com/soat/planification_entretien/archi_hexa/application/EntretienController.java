package com.soat.planification_entretien.archi_hexa.application;

import java.util.List;

import com.soat.planification_entretien.archi_hexa.domain.model.EntretienDetail;
import com.soat.planification_entretien.archi_hexa.domain.use_case.PlanifierEntretien;
import com.soat.planification_entretien.archi_hexa.domain.use_case.ListerEntretien;
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
    private final ListerEntretien listerEntretien;

    public EntretienController(PlanifierEntretien planifierEntretien, ListerEntretien listerEntretien) {
        this.planifierEntretien = planifierEntretien;
        this.listerEntretien = listerEntretien;
    }

    @GetMapping("/")
    public ResponseEntity<List<EntretienDetail>> findAll() {
        return new ResponseEntity<>(listerEntretien.execute(), HttpStatus.OK);
    }

    @PostMapping("planifier")
    public ResponseEntity<Void> planifier(@RequestBody JsonEntretien jsonEntretien) {

        var planifie = planifierEntretien.planifier(jsonEntretien.candidatId(), jsonEntretien.recruteurId(), jsonEntretien.disponibiliteDuCandidat(), jsonEntretien.disponibiliteDuRecruteur());

        if (planifie) {
            return created(null).build();
        } else {
            return badRequest().build();
        }

    }
}

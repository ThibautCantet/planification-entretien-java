package com.soat.planification_entretien.infrastructure.planification.controller;

import java.util.List;

import com.soat.planification_entretien.domain.planification.EntretienRepository;
import com.soat.planification_entretien.use_case.planification.ListerEntretiens;
import com.soat.planification_entretien.use_case.planification.PlanifierEntretien;
import com.soat.planification_entretien.use_case.planification.ValiderEntretien;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
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
    private final ValiderEntretien validerEntretien;
    private final ListerEntretiens listerEntretiens;

    private final EntretienRepository entretienRepository;

    public EntretienController(PlanifierEntretien planifierEntretien, ValiderEntretien validerEntretien, ListerEntretiens listerEntretiens, EntretienRepository entretienRepository) {
        this.planifierEntretien = planifierEntretien;
        this.validerEntretien = validerEntretien;
        this.listerEntretiens = listerEntretiens;
        this.entretienRepository = entretienRepository;
    }

    @GetMapping("/")
    public ResponseEntity<List<EntretienDetailDto>> findAll() {
        var entretiens = listerEntretiens.execute()
                .stream()
                .map(e -> new EntretienDetailDto(e.getId(), e.getEmailCandidat(), e.getEmailRecruteur(), e.getLanguage(), e.getHoraire(), e.getStatus()))
                .toList();
        return new ResponseEntity<>(entretiens, HttpStatus.OK);
    }

    @PostMapping("planifier")
    public ResponseEntity<Void> planifier(@RequestBody EntretienDto entretienDto) {
        var planifie = planifierEntretien.execute(entretienDto.candidatId(), entretienDto.disponibiliteDuCandidat(), entretienDto.disponibiliteDuRecruteur());

        if (planifie) {
            return created(null).build();
        } else {
            return badRequest().build();
        }
    }

    @PatchMapping("/{entretienId}/valider")
    public ResponseEntity<Void> valider(@PathVariable Integer entretienId) {
        var entretienAValider = entretienRepository.findById(entretienId);
        if (entretienAValider == null) {
            return badRequest().build();
        }
        boolean valide = validerEntretien.execute(entretienId);

        if (valide) {
            return noContent().build();
        } else {
            return badRequest().build();
        }
    }
}

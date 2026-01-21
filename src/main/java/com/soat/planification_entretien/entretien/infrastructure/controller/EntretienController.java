package com.soat.planification_entretien.entretien.infrastructure.controller;

import java.util.List;

import com.soat.planification_entretien.candidat.domain.CandidatRepository;
import com.soat.planification_entretien.entretien.use_case.AnnulerEntretien;
import com.soat.planification_entretien.entretien.use_case.ListerEntretiens;
import com.soat.planification_entretien.entretien.use_case.PlanifierEntretien;
import com.soat.planification_entretien.entretien.use_case.ValiderEntretien;
import com.soat.planification_entretien.recruteur.domain.RecruteurRepository;
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
    private final AnnulerEntretien annulerEntretien;
    private final ListerEntretiens listerEntretiens;

    public EntretienController(PlanifierEntretien planifierEntretien, ValiderEntretien validerEntretien, AnnulerEntretien annulerEntretien,
                               ListerEntretiens listerEntretiens, CandidatRepository candidatRepository,
                               RecruteurRepository recruteurRepository) {
        this.planifierEntretien = planifierEntretien;
        this.validerEntretien = validerEntretien;
        this.annulerEntretien = annulerEntretien;
        this.listerEntretiens = listerEntretiens;
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
        var planifie = planifierEntretien.execute(entretienDto.candidatId(), entretienDto.disponibiliteDuCandidat());
        if (planifie) {
            return created(null).build();
        } else {
            return badRequest().build();
        }

    }

    @PatchMapping("/{entretienId}/valider")
    public ResponseEntity<Void> valider(@PathVariable int entretienId) {
        var valide = validerEntretien.execute(entretienId);
        if (valide) {
            return noContent().build();
        } else {
            return badRequest().build();
        }
    }

    @PatchMapping("/{entretienId}/annuler")
    public ResponseEntity<Void> annuler(@PathVariable int entretienId) {
        var annule = annulerEntretien.execute(entretienId);
        if (annule) {
            return noContent().build();
        } else {
            return badRequest().build();
        }
    }

}

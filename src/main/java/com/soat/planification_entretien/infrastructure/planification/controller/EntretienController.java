package com.soat.planification_entretien.infrastructure.planification.controller;

import java.util.List;

import com.soat.planification_entretien.domain.Profil;
import com.soat.planification_entretien.domain.planification.CandidatSuivi;
import com.soat.planification_entretien.domain.planification.EntretienRepository;
import com.soat.planification_entretien.domain.planification.RecruteurEngagé;
import com.soat.planification_entretien.domain.preparation.CandidatRepository;
import com.soat.planification_entretien.domain.preparation.RecruteurRepository;
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
    private final CandidatRepository candidatRepository;
    private final RecruteurRepository recruteurRepository;
    private final EntretienRepository entretienRepository;

    public EntretienController(PlanifierEntretien planifierEntretien, ValiderEntretien validerEntretien, ListerEntretiens listerEntretiens, CandidatRepository candidatRepository, RecruteurRepository recruteurRepository, EntretienRepository entretienRepository) {
        this.planifierEntretien = planifierEntretien;
        this.validerEntretien = validerEntretien;
        this.listerEntretiens = listerEntretiens;
        this.candidatRepository = candidatRepository;
        this.recruteurRepository = recruteurRepository;
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

        var candidat = candidatRepository.findById(entretienDto.candidatId())
                .map(c -> new CandidatSuivi(c.getId(),
                        c.getEmail(), new Profil(c.getLanguage(), c.getExperienceInYears())));
        if (candidat.isEmpty()) {
            return badRequest().build();
        }
        var recruteur = recruteurRepository.findById(entretienDto.recruteurId())
                .map(r -> new RecruteurEngagé(r.getId(),
                        r.getEmail(), new Profil(r.getLanguage(), r.getExperienceInYears())));;
        if (recruteur.isEmpty()) {
            return badRequest().build();
        }
        var planifie = planifierEntretien.execute(candidat.get(), recruteur.get(), entretienDto.disponibiliteDuCandidat(), entretienDto.disponibiliteDuRecruteur());

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

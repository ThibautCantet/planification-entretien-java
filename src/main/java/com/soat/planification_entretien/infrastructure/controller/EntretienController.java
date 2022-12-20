package com.soat.planification_entretien.infrastructure.controller;

import java.util.List;
import java.util.Optional;

import com.soat.planification_entretien.domain.entretien.Candidat;
import com.soat.planification_entretien.domain.candidat.CandidatRepository;
import com.soat.planification_entretien.use_case.ListerEntretiens;
import com.soat.planification_entretien.use_case.PlanifierEntretien;
import com.soat.planification_entretien.domain.recruteur.Recruteur;
import com.soat.planification_entretien.domain.recruteur.RecruteurRepository;
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
    private final ListerEntretiens listerEntretiens;
    private final CandidatRepository candidatRepository;
    private final RecruteurRepository recruteurRepository;

    public EntretienController(PlanifierEntretien planifierEntretien, ListerEntretiens listerEntretiens, CandidatRepository candidatRepository, RecruteurRepository recruteurRepository) {
        this.planifierEntretien = planifierEntretien;
        this.listerEntretiens = listerEntretiens;
        this.candidatRepository = candidatRepository;
        this.recruteurRepository = recruteurRepository;
    }

    @GetMapping("/")
    public ResponseEntity<List<EntretienDetailDto>> findAll() {
        var entretiens = listerEntretiens.execute()
                .stream()
                .map(e -> new EntretienDetailDto(e.getId(), e.getEmailCandidat(), e.getEmailRecruteur(), e.getLanguage(), e.getHoraire()))
                .toList();
        return new ResponseEntity<>(entretiens, HttpStatus.OK);
    }

    @PostMapping("planifier")
    public ResponseEntity<Void> planifier(@RequestBody EntretienDto entretienDto) {

        var candidat = candidatRepository.findById(entretienDto.candidatId())
                .map(c -> new Candidat(c.getId(), c.getLanguage(), c.getAdresseEmail(), c.getExperienceInYears()));
        if (candidat.isEmpty()) {
            return badRequest().build();
        }
        Optional<Recruteur> recruteur = recruteurRepository.findById(entretienDto.recruteurId());
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
}

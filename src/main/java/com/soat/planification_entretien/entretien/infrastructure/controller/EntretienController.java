package com.soat.planification_entretien.entretien.infrastructure.controller;

import java.util.List;
import java.util.Optional;

import com.soat.planification_entretien.entretien.domain.Candidat;
import com.soat.planification_entretien.entretien.domain.CandidatRepository;
import com.soat.planification_entretien.entretien.domain.ConsultantRecruteur;
import com.soat.planification_entretien.entretien.domain.ConsultantRecruteurRepository;
import com.soat.planification_entretien.entretien.domain.EntretienId;
import com.soat.planification_entretien.entretien.use_case.AnnulerEntretien;
import com.soat.planification_entretien.entretien.use_case.ListerEntretiens;
import com.soat.planification_entretien.entretien.use_case.PlanifierEntretien;
import com.soat.planification_entretien.entretien.use_case.ValiderEntretien;
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
    private final ListerEntretiens listerEntretiens;
    private final CandidatRepository candidatRepository;
    private final ConsultantRecruteurRepository recruteurRepository;
    private final ValiderEntretien validerEntretien;
    private AnnulerEntretien annulerEntretien;

    public EntretienController(PlanifierEntretien planifierEntretien,
                               ListerEntretiens listerEntretiens,
                               CandidatRepository candidatRepository,
                               ConsultantRecruteurRepository recruteurRepository, ValiderEntretien validerEntretien, AnnulerEntretien annulerEntretien) {
        this.planifierEntretien = planifierEntretien;
        this.listerEntretiens = listerEntretiens;
        this.candidatRepository = candidatRepository;
        this.recruteurRepository = recruteurRepository;
        this.validerEntretien = validerEntretien;
        this.annulerEntretien = annulerEntretien;
    }

    @GetMapping("/")
    public ResponseEntity<List<EntretienDetailDto>> findAll() {
        var entretiens = listerEntretiens.execute()
                .stream()
                .map(e -> new EntretienDetailDto(e.getId(), e.getEmailCandidat(), e.getEmailRecruteur(), e.getLanguage(), e.getHoraire()))
                .toList();
        return new ResponseEntity<>(entretiens, HttpStatus.OK);
    }

    @PatchMapping("{id}/valider")
    public void valider(@PathVariable("id") int id) {

        validerEntretien.execute(new EntretienId(id));
    }

    @PatchMapping("{id}/annuler")
    public void annuler(@PathVariable("id") int id) {

        annulerEntretien.execute(new EntretienId(id));
    }

    @PostMapping("planifier")
    public ResponseEntity<Void> planifier(@RequestBody EntretienDto entretienDto) {

        Optional<Candidat> candidat = candidatRepository.findById(entretienDto.candidatId());
        if (candidat.isEmpty()) {
            return badRequest().build();
        }
        Optional<ConsultantRecruteur> recruteur = recruteurRepository.findById(entretienDto.recruteurId());
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

package com.soat.planification_entretien.entretien.infrastructure.controller;

import java.util.List;
import java.util.Optional;

import com.soat.planification_entretien.entretien.application_service.PlanifierEntretienCommand;
import com.soat.planification_entretien.entretien.application_service.ValiderEntretienCommand;
import com.soat.planification_entretien.entretien.domain.Candidat;
import com.soat.planification_entretien.candidat.domain.CandidatRepository;
import com.soat.planification_entretien.entretien.domain.Entretien;
import com.soat.planification_entretien.entretien.application_service.ListerEntretiensQueryHandler;
import com.soat.planification_entretien.entretien.application_service.PlanifierEntretienCommandHandler;
import com.soat.planification_entretien.entretien.domain.Recruteur;
import com.soat.planification_entretien.recruteur.domain.RecruteurRepository;
import com.soat.planification_entretien.entretien.application_service.ValiderEntretienCommandHandler;
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

    private final PlanifierEntretienCommandHandler planifierEntretienCommandHandler;
    private final ListerEntretiensQueryHandler listerEntretiensQueryHandler;
    private final CandidatRepository candidatRepository;
    private final RecruteurRepository recruteurRepository;
    private final ValiderEntretienCommandHandler validerEntretienCommandHandler;

    public EntretienController(PlanifierEntretienCommandHandler planifierEntretienCommandHandler, ListerEntretiensQueryHandler listerEntretiensQueryHandler, CandidatRepository candidatRepository, RecruteurRepository recruteurRepository, ValiderEntretienCommandHandler validerEntretienCommandHandler) {
        this.planifierEntretienCommandHandler = planifierEntretienCommandHandler;
        this.listerEntretiensQueryHandler = listerEntretiensQueryHandler;
        this.candidatRepository = candidatRepository;
        this.recruteurRepository = recruteurRepository;
        this.validerEntretienCommandHandler = validerEntretienCommandHandler;
    }

    @GetMapping("/")
    public ResponseEntity<List<EntretienDetailDto>> findAll() {
        var entretiens = listerEntretiensQueryHandler.handle()
                .stream()
                .map(e -> new EntretienDetailDto(e.getId(), e.getEmailCandidat(), e.getEmailRecruteur(), e.getLanguage(), e.getHoraire(), e.getStatus()))
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
        var recruteur = recruteurRepository.findById(entretienDto.recruteurId())
                .map(c -> new Recruteur(c.getId(), c.getLanguage(), c.getAdresseEmail(), c.getExperienceInYears()));

        if (recruteur.isEmpty()) {
            return badRequest().build();
        }
        var planifie = planifierEntretienCommandHandler.handle(new PlanifierEntretienCommand(candidat.get(), recruteur.get(), entretienDto.disponibiliteDuCandidat(), entretienDto.disponibiliteDuRecruteur()));

        if (planifie) {
            return created(null).build();
        } else {
            return badRequest().build();
        }
    }

    @PatchMapping("{id}/valider")
    public ResponseEntity<Void> valider(@PathVariable("id") int id) {
        Optional<Entretien> maybeEntretien = validerEntretienCommandHandler.handle(new ValiderEntretienCommand(id));

        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}

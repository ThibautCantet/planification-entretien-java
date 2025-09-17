package com.soat.planification_entretien.entretien.command.infrastructure.controller;

import java.util.Optional;

import com.soat.planification_entretien.candidat.command.domain.port.repository.CandidatRepository;
import com.soat.planification_entretien.entretien.command.application_service.PlanifierEntretienCommandHandler;
import com.soat.planification_entretien.entretien.command.application_service.ValiderEntretienCommandHandler;
import com.soat.planification_entretien.entretien.domain.model.Candidat;
import com.soat.planification_entretien.entretien.domain.model.Entretien;
import com.soat.planification_entretien.entretien.domain.model.Recruteur;
import com.soat.planification_entretien.recruteur.command.domain.port.repository.RecruteurRepository;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import static org.springframework.http.ResponseEntity.*;

@RestController
@RequestMapping(EntretienCommandController.PATH)
public class EntretienCommandController {
    public static final String PATH = "/api/entretien/";

    private final PlanifierEntretienCommandHandler planifierEntretienCommandHandler;
    private final CandidatRepository candidatRepository;
    private final RecruteurRepository recruteurRepository;
    private final ValiderEntretienCommandHandler validerEntretienCommandHandler;

    public EntretienCommandController(PlanifierEntretienCommandHandler planifierEntretienCommandHandler, CandidatRepository candidatRepository, RecruteurRepository recruteurRepository, ValiderEntretienCommandHandler validerEntretienCommandHandler) {
        this.planifierEntretienCommandHandler = planifierEntretienCommandHandler;
        this.candidatRepository = candidatRepository;
        this.recruteurRepository = recruteurRepository;
        this.validerEntretienCommandHandler = validerEntretienCommandHandler;
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
        var planifie = planifierEntretienCommandHandler.handle(new PlanifierEntretienCommandHandler.PlanifierEntretienCommand(candidat.get(), recruteur.get(), entretienDto.disponibiliteDuCandidat(), entretienDto.disponibiliteDuRecruteur()));

        if (planifie) {
            return created(null).build();
        } else {
            return badRequest().build();
        }
    }

    @PatchMapping("{id}/valider")
    public ResponseEntity<Void> valider(@PathVariable("id") int id) {
        Optional<Entretien> maybeEntretien = validerEntretienCommandHandler.handle(
                new ValiderEntretienCommandHandler.ValiderEntretienCommand(id));

        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}

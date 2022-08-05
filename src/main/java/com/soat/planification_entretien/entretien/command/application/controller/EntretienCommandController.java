package com.soat.planification_entretien.entretien.command.application.controller;

import java.net.URI;
import java.util.List;

import com.soat.planification_entretien.candidat.command.repository.CandidatRepository;
import com.soat.planification_entretien.entretien.command.MettreAJourStatusEntretienCommand;
import com.soat.planification_entretien.entretien.command.PlanifierEntretienAutomatiqueCommand;
import com.soat.planification_entretien.entretien.command.PlanifierEntretienCommand;
import com.soat.planification_entretien.entretien.command.domain.entity.Candidat;
import com.soat.planification_entretien.entretien.command.domain.entity.Recruteur;
import com.soat.planification_entretien.entretien.event.EntretienNonTrouve;
import com.soat.planification_entretien.entretien.event.EntretienPlanifie;
import com.soat.planification_entretien.infrastructure.controller.CommandController;
import com.soat.planification_entretien.infrastructure.middleware.command.CommandBusFactory;
import com.soat.planification_entretien.recruteur.command.domain.repository.RecruteurRepository;
import com.soat.planification_entretien.rendez_vous.command.domain.entity.Calendrier;
import com.soat.planification_entretien.rendez_vous.command.repository.CalendrierRepository;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import static java.util.Optional.*;
import static org.springframework.http.ResponseEntity.*;

@RestController
@RequestMapping(EntretienCommandController.PATH)
public class EntretienCommandController extends CommandController {
    public static final String PATH = "/api/entretien/";


    private final CandidatRepository candidatRepository;
    private final RecruteurRepository recruteurRepository;
    private final CalendrierRepository calendrierRepository;

    public EntretienCommandController(CommandBusFactory commandBusFactory, CandidatRepository candidatRepository, RecruteurRepository recruteurRepository, CalendrierRepository calendrierRepository) {
        super(commandBusFactory);
        commandBusFactory.build();
        this.candidatRepository = candidatRepository;
        this.recruteurRepository = recruteurRepository;
        this.calendrierRepository = calendrierRepository;
    }

    @PostMapping("planifier")
    public ResponseEntity<Integer> planifier(@RequestBody EntretienDto entretienDto) {

        var candidat = candidatRepository.findById(entretienDto.candidatId()).map(c ->
                new Candidat(c.getId(), c.getLanguage(), c.getEmail(), c.getExperienceInYears())
        );
        if (candidat.isEmpty()) {
            return badRequest().build();
        }
        var recruteur = recruteurRepository.findById(entretienDto.recruteurId()).map(r -> {
            var calendrier = calendrierRepository.findByRecruteur(r.getEmail()).orElse(null);
            var rendezVous = ofNullable(calendrier).map(Calendrier::rendezVous).orElse(List.of())
                    .stream()
                    .map(rdv -> new com.soat.planification_entretien.entretien.command.domain.entity.RendezVous(rdv.emailCandidat(), rdv.horaire()))
                    .toList();
            return new Recruteur(r.getId(), r.getLanguage(), r.getEmail(), r.getExperienceInYears(), rendezVous);
        });
        if (recruteur.isEmpty()) {
            return badRequest().build();
        }
        var commandResponse = getCommandBus().dispatch(new PlanifierEntretienCommand(candidat.get(), recruteur.get(), entretienDto.disponibiliteDuCandidat()));

        return (ResponseEntity<Integer>) commandResponse.findFirst(EntretienPlanifie.class)
                .map(EntretienPlanifie.class::cast)
                .map(event -> created(URI.create(PATH + "/" + ((EntretienPlanifie) event).id()))
                        .body(((EntretienPlanifie) event).id()))
                .orElse(badRequest().build());
    }


    @PostMapping("planifier-automatique")
    public ResponseEntity<Void> planifierAutomatique(@RequestBody EntretienAutomatiqueDto entretienAutomatiqueDto) {

        var candidat = candidatRepository.findById(entretienAutomatiqueDto.candidatId()).map(c ->
                new Candidat(c.getId(), c.getLanguage(), c.getEmail(), c.getExperienceInYears())
        );
        if (candidat.isEmpty()) {
            return badRequest().build();
        }

        var commandResponse = getCommandBus().dispatch(new PlanifierEntretienAutomatiqueCommand(candidat.get(), entretienAutomatiqueDto.disponibiliteDuCandidat()));

        return (ResponseEntity<Void>) commandResponse.findFirst(EntretienPlanifie.class)
                .map(EntretienPlanifie.class::cast)
                .map(event -> created(URI.create(PATH + "/" + ((EntretienPlanifie) event).id()))
                        .body(((EntretienPlanifie) event).id()))
                .orElse(badRequest().build());
    }

    @PutMapping("{id}")
    public ResponseEntity<Void> mettreAJourStatus(@PathVariable("id") String id, @RequestBody StatutDto statutDto) {
        var commandResponse = getCommandBus().dispatch(new MettreAJourStatusEntretienCommand(id, statutDto.status()));

        return (ResponseEntity<Void>) commandResponse.findFirst(EntretienNonTrouve.class)
                .map(unused -> new ResponseEntity<>(HttpStatus.BAD_REQUEST))
                .orElse(new ResponseEntity<>(HttpStatus.OK));
    }
}

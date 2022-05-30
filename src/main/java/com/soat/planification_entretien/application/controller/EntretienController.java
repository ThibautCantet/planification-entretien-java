package com.soat.planification_entretien.application.controller;

import java.net.URI;
import java.util.Optional;

import com.soat.planification_entretien.domain.candidat.Candidat;
import com.soat.planification_entretien.domain.candidat.CandidatRepository;
import com.soat.planification_entretien.domain.entretien.PlanifierEntretienCommand;
import com.soat.planification_entretien.domain.entretien.event.EntretienPlanifie;
import com.soat.planification_entretien.domain.recruteur.Recruteur;
import com.soat.planification_entretien.domain.recruteur.RecruteurRepository;
import com.soat.planification_entretien.infrastructure.controller.CommandController;
import com.soat.planification_entretien.infrastructure.middleware.command.CommandBusFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import static org.springframework.http.ResponseEntity.*;

@RestController
@RequestMapping(EntretienController.PATH)
public class EntretienController extends CommandController {
    public static final String PATH = "/api/entretien/";

    private final CandidatRepository candidatRepository;
    private final RecruteurRepository recruteurRepository;

    public EntretienController(CommandBusFactory commandBusFactory, CandidatRepository candidatRepository, RecruteurRepository recruteurRepository) {
        super(commandBusFactory);
        this.candidatRepository = candidatRepository;
        this.recruteurRepository = recruteurRepository;
    }

    @PostMapping("planifier")
    public ResponseEntity<Void> planifier(@RequestBody EntretienDto entretienDto) {

        Optional<Candidat> candidat = candidatRepository.findById(entretienDto.candidatId());
        if (candidat.isEmpty()) {
            return badRequest().build();
        }
        Optional<Recruteur> recruteur = recruteurRepository.findById(entretienDto.recruteurId());
        if (recruteur.isEmpty()) {
            return badRequest().build();
        }
        PlanifierEntretienCommand command = new PlanifierEntretienCommand(candidat.get(), recruteur.get(), entretienDto.disponibiliteDuCandidat(), entretienDto.disponibiliteDuRecruteur());
        var commandResponse = getCommandBus().dispatch(command);
        if (commandResponse.event() instanceof EntretienPlanifie) {
            return created(URI.create(PATH + "/" + commandResponse.value())).build();
        } else {
            return badRequest().build();
        }

    }
}

package com.soat.planification_entretien.application.controller.command;

import java.net.URI;
import java.util.Optional;

import com.soat.planification_entretien.domain.candidat.entity.Candidat;
import com.soat.planification_entretien.domain.candidat.repository.CandidatRepository;
import com.soat.planification_entretien.domain.entretien.event.EntretienPlanifie;
import com.soat.planification_entretien.domain.entretien.command.PlanifierEntretienCommand;
import com.soat.planification_entretien.domain.recruteur.command.entity.Recruteur;
import com.soat.planification_entretien.domain.recruteur.command.repository.RecruteurRepository;
import com.soat.planification_entretien.infrastructure.controller.CommandController;
import com.soat.planification_entretien.infrastructure.middleware.command.CommandBusFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import static org.springframework.http.ResponseEntity.*;

@RestController
@RequestMapping(EntretienCommandController.PATH)
public class EntretienCommandController extends CommandController {
    public static final String PATH = "/api/entretien/";


    private final CandidatRepository candidatRepository;
    private final RecruteurRepository recruteurRepository;

    public EntretienCommandController(CommandBusFactory commandBusFactory, CandidatRepository candidatRepository, RecruteurRepository recruteurRepository) {
        super(commandBusFactory);
        commandBusFactory.build();
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
        var commandResponse = getCommandBus().dispatch(new PlanifierEntretienCommand(candidat.get(), recruteur.get(), entretienDto.disponibiliteDuCandidat(), entretienDto.disponibiliteDuRecruteur()));

        if (commandResponse.containEventType(EntretienPlanifie.class)) {
            return created(URI.create(PATH + "/" + commandResponse.value())).build();
        } else {
            return badRequest().build();
        }

    }
}

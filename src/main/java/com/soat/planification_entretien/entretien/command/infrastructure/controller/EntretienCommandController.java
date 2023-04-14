package com.soat.planification_entretien.entretien.command.infrastructure.controller;

import java.util.Optional;

import com.soat.planification_entretien.candidat.command.domain.CandidatRepository;
import com.soat.planification_entretien.common.cqrs.application.CommandController;
import com.soat.planification_entretien.common.cqrs.command.CommandResponse;
import com.soat.planification_entretien.common.cqrs.middleware.command.CommandBusFactory;
import com.soat.planification_entretien.entretien.command.AnnulerEntretienCommand;
import com.soat.planification_entretien.entretien.command.PlanifierEntretienCommand;
import com.soat.planification_entretien.entretien.command.ValiderEntretienCommand;
import com.soat.planification_entretien.entretien.command.ValiderEntretienCommandHandler;
import com.soat.planification_entretien.entretien.command.domain.Candidat;
import com.soat.planification_entretien.entretien.command.domain.Entretien;
import com.soat.planification_entretien.entretien.command.domain.EntretienAnnulé;
import com.soat.planification_entretien.entretien.command.domain.EntretienCréé;
import com.soat.planification_entretien.entretien.command.domain.EntretienValidé;
import com.soat.planification_entretien.entretien.command.domain.Recruteur;
import com.soat.planification_entretien.recruteur.command.domain.RecruteurRepository;
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
public class EntretienCommandController extends CommandController {
    public static final String PATH = "/api/entretien/";

    private final CandidatRepository candidatRepository;
    private final RecruteurRepository recruteurRepository;

    public EntretienCommandController(CommandBusFactory commandBusFactory, CandidatRepository candidatRepository, RecruteurRepository recruteurRepository) {
        super(commandBusFactory);
        this.candidatRepository = candidatRepository;
        this.recruteurRepository = recruteurRepository;
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

        var commandResponse = getCommandBus().dispatch(new PlanifierEntretienCommand(candidat.get(), recruteur.get(), entretienDto.disponibiliteDuCandidat(), entretienDto.disponibiliteDuRecruteur()));

        if (commandResponse.findFirst(EntretienCréé.class).isPresent()) {
            return created(null).build();
        } else {
            return badRequest().build();
        }
    }

    @PatchMapping("{id}/valider")
    public ResponseEntity<Void> valider(@PathVariable("id") int id) {
        var commandResponse = getCommandBus().dispatch(new ValiderEntretienCommand(id));
        if (commandResponse.findFirst(EntretienValidé.class).isPresent()) {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    @PatchMapping("{id}/annuler")
    public ResponseEntity<Void> annuler(@PathVariable("id") int id) {
        var commandResponse = getCommandBus().dispatch(new AnnulerEntretienCommand(id));
        if (commandResponse.findFirst(EntretienAnnulé.class).isPresent()) {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }
}

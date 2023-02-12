package com.soat.planification_entretien.recruteur.command.infrastucture.controller;

import com.soat.planification_entretien.recruteur.command.CreerRecruteurCommand;
import com.soat.planification_entretien.recruteur.command.CreerRecruteurCommandHandler;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import static org.springframework.http.ResponseEntity.*;

@RestController
@RequestMapping(RecruteurCommandController.PATH)
public class RecruteurCommandController {
    public static final String PATH = "/api/recruteur";

    private final CreerRecruteurCommandHandler creerRecruteurCommandHandler;

    public RecruteurCommandController(CreerRecruteurCommandHandler creerRecruteurCommandHandler) {
        this.creerRecruteurCommandHandler = creerRecruteurCommandHandler;
    }

    @PostMapping("")
    public ResponseEntity<Integer> creer(@RequestBody RecruteurDto recruteurDto) {
        if (validExperience(recruteurDto)) {
            return badRequest().build();
        }
        Integer createdRecruteurId = creerRecruteurCommandHandler.handle(new CreerRecruteurCommand(recruteurDto.language(), recruteurDto.email(), recruteurDto.experienceEnAnnees()));
        if (createdRecruteurId == null) {
            return badRequest().build();
        }

        return created(null).body(createdRecruteurId);
    }

    private static boolean validExperience(RecruteurDto candidatDto) {
        try {
            Integer.parseInt(candidatDto.experienceEnAnnees());
            return candidatDto.experienceEnAnnees().isBlank();
        } catch (Exception e) {
            return false;
        }
    }

}

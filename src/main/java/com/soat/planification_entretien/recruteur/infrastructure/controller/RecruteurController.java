package com.soat.planification_entretien.recruteur.infrastructure.controller;

import java.util.List;

import com.soat.planification_entretien.recruteur.application_service.CreerRecruteurCommandHandler;
import com.soat.planification_entretien.recruteur.application_service.ListerRecruteursExperimentesQueryHandler;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import static org.springframework.http.ResponseEntity.*;

@RestController
@RequestMapping(RecruteurController.PATH)
public class RecruteurController {
    public static final String PATH = "/api/recruteur";

    private final CreerRecruteurCommandHandler creerRecruteurCommandHandler;
    private final ListerRecruteursExperimentesQueryHandler listerRecruteursExperimentesQueryHandler;

    public RecruteurController(CreerRecruteurCommandHandler creerRecruteurCommandHandler, ListerRecruteursExperimentesQueryHandler listerRecruteursExperimentesQueryHandler) {
        this.creerRecruteurCommandHandler = creerRecruteurCommandHandler;
        this.listerRecruteursExperimentesQueryHandler = listerRecruteursExperimentesQueryHandler;
    }

    @PostMapping("")
    public ResponseEntity<Integer> creer(@RequestBody RecruteurDto recruteurDto) {
        if (validExperience(recruteurDto)) {
            return badRequest().build();
        }
        Integer createdRecruteurId = creerRecruteurCommandHandler.execute(recruteurDto.language(), recruteurDto.email(), recruteurDto.experienceEnAnnees());
        if (createdRecruteurId == null) {
            return badRequest().build();
        }

        return created(null).body(createdRecruteurId);
    }

    @GetMapping("")
    public ResponseEntity<List<RecruteurDetailDto>> lister() {
        List<RecruteurDetailDto> recruteurs = listerRecruteursExperimentesQueryHandler.execute().stream()
                .map(e -> new RecruteurDetailDto(e.getId(), e.getLanguage(), e.getExperienceInYears(), e.getAdresseEmail()))
                .toList();

        return new ResponseEntity<>(recruteurs, HttpStatus.OK);
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

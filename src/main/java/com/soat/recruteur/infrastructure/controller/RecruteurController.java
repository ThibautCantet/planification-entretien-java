package com.soat.recruteur.infrastructure.controller;

import java.util.List;
import java.util.UUID;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import com.soat.recruteur.domain.Recruteur;
import com.soat.recruteur.use_case.CreerRecruteur;
import com.soat.recruteur.use_case.ListerRecruteursExperimentes;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import static java.util.Optional.*;
import static org.springframework.http.ResponseEntity.*;

@RestController
@RequestMapping(RecruteurController.PATH)
public class RecruteurController {
    private static final String EMAIL_REGEX = "^[\\w-_.+]*[\\w-_.]@([\\w]+\\.)+[\\w]+[\\w]$";
    public static final String PATH = "/api/recruteur";

    private final CreerRecruteur creerRecruteur;
    private final ListerRecruteursExperimentes listerRecruteursExperimentes;

    public RecruteurController(CreerRecruteur creerRecruteur, ListerRecruteursExperimentes listerRecruteursExperimentes) {
        this.creerRecruteur = creerRecruteur;
        this.listerRecruteursExperimentes = listerRecruteursExperimentes;
    }

    @PostMapping("")
    public ResponseEntity<UUID> creer(@RequestBody RecruteurDto recruteurDto) {
        Recruteur recruteur = creerRecruteur.execute(recruteurDto.language(), recruteurDto.email(), recruteurDto.experienceEnAnnees());
        return ofNullable(recruteur).map(c -> created(null).body(c.getId()))
                .orElse(badRequest().build());
    }

    @GetMapping("")
    public ResponseEntity<List<RecruteurDetailDto>> findAll() {
        List<RecruteurDetailDto> entretienDetails = listerRecruteursExperimentes.execute().stream()
                .map(r -> new RecruteurDetailDto(r.id(), r.email(), r.competence()))
                .toList();
        return new ResponseEntity<>(entretienDetails, HttpStatus.OK);
    }

    private static boolean isEmail(String adresse) {
        final Pattern r = Pattern.compile(EMAIL_REGEX);
        final Matcher m = r.matcher(adresse);
        return m.matches();
    }
}

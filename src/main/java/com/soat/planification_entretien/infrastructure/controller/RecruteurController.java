package com.soat.planification_entretien.infrastructure.controller;

import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import com.soat.planification_entretien.application.use_case.ListerEntretien;
import com.soat.planification_entretien.application.use_case.ListerRecruteursExperimentes;
import com.soat.planification_entretien.application.use_case.output_port.RecruteurPort;
import com.soat.planification_entretien.domain.model.Recruteur;
import com.soat.planification_entretien.domain.model.RecruteurDetail;
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
    private static final String EMAIL_REGEX = "^[\\w-_.+]*[\\w-_.]@([\\w]+\\.)+[\\w]+[\\w]$";
    public static final String PATH = "/api/recruteur";

    private final RecruteurPort recruteurPort;
    private final ListerRecruteursExperimentes listerRecruteursExperimentes;

    public RecruteurController(RecruteurPort recruteurPort, ListerRecruteursExperimentes listerRecruteursExperimentes) {
        this.recruteurPort = recruteurPort;
        this.listerRecruteursExperimentes = listerRecruteursExperimentes;
    }

    @PostMapping
    public ResponseEntity<Integer> creer(@RequestBody RecruteurDto recruteurDto) {
        if (recruteurDto.language().isBlank() || !isEmail(recruteurDto.email()) || recruteurDto.experienceEnAnnees().isBlank() || Integer.parseInt(recruteurDto.experienceEnAnnees()) < 0) {
            return badRequest().build();
        }

        Recruteur recruteur = new Recruteur(recruteurDto.language(), recruteurDto.email(), Integer.parseInt(recruteurDto.experienceEnAnnees()));
        var savedRecruteurId = recruteurPort.save(recruteur);

        return created(null).body(savedRecruteurId);
    }

    private static boolean isEmail(String adresse) {
        final Pattern r = Pattern.compile(EMAIL_REGEX);
        final Matcher m = r.matcher(adresse);
        return m.matches();
    }

    @GetMapping("")
    public ResponseEntity<List<RecruteurDetail>> findAll() {
        List<RecruteurDetail> entretienDetails = listerRecruteursExperimentes.execute().stream()
                .toList();
        return new ResponseEntity<>(entretienDetails, HttpStatus.OK);
    }
}

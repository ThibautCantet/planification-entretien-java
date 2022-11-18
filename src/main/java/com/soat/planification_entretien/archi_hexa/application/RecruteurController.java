package com.soat.planification_entretien.archi_hexa.application;

import com.soat.planification_entretien.archi_hexa.domain.entity.Recruteur;
import com.soat.planification_entretien.archi_hexa.domain.entity.RecruteurExperimente;
import com.soat.planification_entretien.archi_hexa.domain.use_case.CreerRecruteur;
import com.soat.planification_entretien.archi_hexa.domain.use_case.ListerRecruteurExperimente;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import static org.springframework.http.ResponseEntity.badRequest;
import static org.springframework.http.ResponseEntity.created;

@RestController
@RequestMapping(RecruteurController.PATH)
public class RecruteurController {
    private static final String EMAIL_REGEX = "^[\\w-_.+]*[\\w-_.]@([\\w]+\\.)+[\\w]+[\\w]$";
    public static final String PATH = "/api/recruteur";

    private final CreerRecruteur creerRecruteur;
    private final ListerRecruteurExperimente listerRecruteurExperimente;

    public RecruteurController(CreerRecruteur creerRecruteur, ListerRecruteurExperimente listerRecruteurExperimente) {
        this.creerRecruteur = creerRecruteur;
        this.listerRecruteurExperimente = listerRecruteurExperimente;
    }

    @PostMapping("")
    public ResponseEntity<Integer> creer(@RequestBody RecruteurDto recruteurDto) {
        if (recruteurDto.language().isBlank() || !isEmail(recruteurDto.email()) || recruteurDto.experienceEnAnnees().isBlank() || Integer.parseInt(recruteurDto.experienceEnAnnees()) < 0) {
            return badRequest().build();
        }

        Recruteur recruteur = toRecruteur(recruteurDto);
        final Integer savedRecruteurId = creerRecruteur.execute(recruteur);
        return created(null).body(savedRecruteurId);
    }

    @GetMapping("/experimente")
    public ResponseEntity<List<RecruteurExperimenteDto>> experimente() {
        var recruteurs = listerRecruteurExperimente.execute()
                .stream()
                .map(r -> new RecruteurExperimenteDto(r.email(), r.languageXP()))
                .toList();
        return new ResponseEntity<>(recruteurs, HttpStatus.OK);
    }

    private static Recruteur toRecruteur(RecruteurDto recruteurDto) {
        return new Recruteur(null, recruteurDto.language(), recruteurDto.email(), Integer.parseInt(recruteurDto.experienceEnAnnees()));
    }

    private static boolean isEmail(String adresse) {
        final Pattern r = Pattern.compile(EMAIL_REGEX);
        final Matcher m = r.matcher(adresse);
        return m.matches();
    }
}

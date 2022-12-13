package com.soat.planification_entretien.archi_hexa.application;

import static org.springframework.http.ResponseEntity.badRequest;
import static org.springframework.http.ResponseEntity.created;

import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.soat.planification_entretien.archi_hexa.domain.model.Recruteur;
import com.soat.planification_entretien.archi_hexa.domain.model.RecruteurExperimente;
import com.soat.planification_entretien.archi_hexa.domain.use_case.CreerRecruteur;
import com.soat.planification_entretien.archi_hexa.domain.use_case.ListerRecuteurExperimente;

@RestController
@RequestMapping(RecruteurController.PATH)
public class RecruteurController {
    private static final String EMAIL_REGEX = "^[\\w-_.+]*[\\w-_.]@([\\w]+\\.)+[\\w]+[\\w]$";
    public static final String PATH = "/api/recruteur";

    private final CreerRecruteur creerRecruteur;
    private final ListerRecuteurExperimente listerRecuteurExperimente;

    public RecruteurController(CreerRecruteur creerRecruteur, ListerRecuteurExperimente listerRecuteurExperimente) {
        this.creerRecruteur = creerRecruteur;
        this.listerRecuteurExperimente = listerRecuteurExperimente;
    }

    @PostMapping("")
    public ResponseEntity<Integer> creer(@RequestBody JsonRecruteur jsonRecruteur) {
        if (jsonRecruteur.language().isBlank() || !isEmail(jsonRecruteur.email()) || jsonRecruteur.experienceEnAnnees().isBlank() || Integer.parseInt(jsonRecruteur.experienceEnAnnees()) < 0) {
            return badRequest().build();
        }
        Recruteur recruteur = toRecruteur(jsonRecruteur);
        Integer savedRecruteurId = creerRecruteur.execute(recruteur);

        return created(null).body(savedRecruteurId);
    }

    @GetMapping("experimente")
    public ResponseEntity<List<RecruteurExperimente>> recupererExperimente() {
         List<RecruteurExperimente> recruteurs = listerRecuteurExperimente.execute();
         return new ResponseEntity<>(recruteurs, HttpStatus.OK);
    }

    private static Recruteur toRecruteur(JsonRecruteur jsonRecruteur) {
        return new Recruteur(null, jsonRecruteur.language(), jsonRecruteur.email(), Integer.parseInt(jsonRecruteur.experienceEnAnnees()));
    }

    private static boolean isEmail(String adresse) {
        final Pattern r = Pattern.compile(EMAIL_REGEX);
        final Matcher m = r.matcher(adresse);
        return m.matches();
    }
}

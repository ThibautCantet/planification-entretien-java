package com.soat.planification_entretien.archi_hexa.application;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import com.soat.planification_entretien.archi_hexa.domain.model.Recruteur;
import com.soat.planification_entretien.archi_hexa.domain.use_case.CreerRecruteur;
import com.soat.planification_entretien.archi_hexa.infrastructure.jpa.model.JpaRecruteur;
import org.springframework.http.ResponseEntity;
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

    private final CreerRecruteur creerRecruteur;

    public RecruteurController(CreerRecruteur creerRecruteur) {
        this.creerRecruteur = creerRecruteur;
    }

    @PostMapping("")
    public ResponseEntity<Integer> creer(@RequestBody RecruteurDto recruteurDto) {
        if (recruteurDto.language().isBlank() || !isEmail(recruteurDto.email()) || recruteurDto.experienceEnAnnees().isBlank() || Integer.parseInt(recruteurDto.experienceEnAnnees()) < 0) {
            return badRequest().build();
        }
        Recruteur recruteur = toRecruteur(recruteurDto);
        Integer savedRecruteurId = creerRecruteur.execute(recruteur);

        return created(null).body(savedRecruteurId);
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

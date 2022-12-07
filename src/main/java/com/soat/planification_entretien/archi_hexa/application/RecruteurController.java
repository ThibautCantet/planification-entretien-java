package com.soat.planification_entretien.archi_hexa.application;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import com.soat.planification_entretien.archi_hexa.infrastructure.jpa.model.JpaRecruteur;
import com.soat.planification_entretien.archi_hexa.infrastructure.jpa.repository.JpaRecruteurRepository;
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

    private final JpaRecruteurRepository jpaRecruteurRepository;

    public RecruteurController(JpaRecruteurRepository jpaRecruteurRepository) {
        this.jpaRecruteurRepository = jpaRecruteurRepository;
    }

    @PostMapping("")
    public ResponseEntity<Integer> creer(@RequestBody RecruteurDto recruteurDto) {
        if (recruteurDto.language().isBlank() || !isEmail(recruteurDto.email()) || recruteurDto.experienceEnAnnees().isBlank() || Integer.parseInt(recruteurDto.experienceEnAnnees()) < 0) {
            return badRequest().build();
        }

        JpaRecruteur recruteur = new JpaRecruteur(recruteurDto.language(), recruteurDto.email(), Integer.parseInt(recruteurDto.experienceEnAnnees()));
        JpaRecruteur savedRecruteur = jpaRecruteurRepository.save(recruteur);

        return created(null).body(savedRecruteur.getId());
    }

    private static boolean isEmail(String adresse) {
        final Pattern r = Pattern.compile(EMAIL_REGEX);
        final Matcher m = r.matcher(adresse);
        return m.matches();
    }
}

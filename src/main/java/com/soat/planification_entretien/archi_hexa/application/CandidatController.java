package com.soat.planification_entretien.archi_hexa.application;

import static org.springframework.http.ResponseEntity.badRequest;
import static org.springframework.http.ResponseEntity.created;

import java.util.regex.Matcher;
import java.util.regex.Pattern;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.soat.planification_entretien.archi_hexa.domain.model.Candidat;
import com.soat.planification_entretien.archi_hexa.domain.use_case.CreerCandidat;

@RestController
@RequestMapping(CandidatController.PATH)
public class CandidatController {
    private static final String EMAIL_REGEX = "^[\\w-_.+]*[\\w-_.]@([\\w]+\\.)+[\\w]+[\\w]$";

    public static final String PATH = "/api/candidat";

    private final CreerCandidat creerCandidat;

    public CandidatController(CreerCandidat creerCandidat) {
        this.creerCandidat = creerCandidat;
    }

    @PostMapping("")
    public ResponseEntity<Integer> creer(@RequestBody CandidatDto candidatDto) {

        if (candidatDto.language().isBlank() || !isEmail(candidatDto.email()) || candidatDto.experienceEnAnnees().isBlank() || Integer.parseInt(candidatDto.experienceEnAnnees()) < 0) {
            return badRequest().build();
        }

        Candidat candidat = toCandidat(candidatDto);
        Integer savedCandidatId = creerCandidat.execute(candidat);

        return created(null).body(savedCandidatId);
    }

    private static Candidat toCandidat(CandidatDto candidatDto) {
        return new Candidat(null, candidatDto.language(), candidatDto.email(), Integer.parseInt(candidatDto.experienceEnAnnees()));
    }

    private static boolean isEmail(String adresse) {
        final Pattern r = Pattern.compile(EMAIL_REGEX);
        final Matcher m = r.matcher(adresse);
        return m.matches();
    }
}

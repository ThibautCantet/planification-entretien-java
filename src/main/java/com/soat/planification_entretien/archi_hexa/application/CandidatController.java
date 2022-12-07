package com.soat.planification_entretien.archi_hexa.application;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import com.soat.planification_entretien.archi_hexa.infrastructure.jpa.model.JpaCandidat;
import com.soat.planification_entretien.archi_hexa.infrastructure.jpa.repository.JpaCandidatRepository;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import static org.springframework.http.ResponseEntity.*;

@RestController
@RequestMapping(CandidatController.PATH)
public class CandidatController {
    private static final String EMAIL_REGEX = "^[\\w-_.+]*[\\w-_.]@([\\w]+\\.)+[\\w]+[\\w]$";

    public static final String PATH = "/api/candidat";

    private final JpaCandidatRepository jpaCandidatRepository;

    public CandidatController(JpaCandidatRepository jpaCandidatRepository) {
        this.jpaCandidatRepository = jpaCandidatRepository;
    }

    @PostMapping("")
    public ResponseEntity<Integer> creer(@RequestBody CandidatDto candidatDto) {

        if (candidatDto.language().isBlank() || !isEmail(candidatDto.email()) || candidatDto.experienceEnAnnees().isBlank() || Integer.parseInt(candidatDto.experienceEnAnnees()) < 0) {
            return badRequest().build();
        }

        JpaCandidat candidat = new JpaCandidat(candidatDto.language(), candidatDto.email(), Integer.parseInt(candidatDto.experienceEnAnnees()));
        JpaCandidat savedCandidat = jpaCandidatRepository.save(candidat);

        return created(null).body(savedCandidat.getId());
    }

    private static boolean isEmail(String adresse) {
        final Pattern r = Pattern.compile(EMAIL_REGEX);
        final Matcher m = r.matcher(adresse);
        return m.matches();
    }
}

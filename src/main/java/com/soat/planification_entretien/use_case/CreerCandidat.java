package com.soat.planification_entretien.use_case;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import com.soat.planification_entretien.domain.Candidat;
import com.soat.planification_entretien.domain.CandidatRepository;
import org.springframework.stereotype.Service;

@Service
public class CreerCandidat {
    private static final String EMAIL_REGEX = "^[\\w-_.+]*[\\w-_.]@([\\w]+\\.)+[\\w]+[\\w]$";

    private final CandidatRepository candidatRepository;

    public CreerCandidat(CandidatRepository candidatRepository) {
        this.candidatRepository = candidatRepository;
    }

    public Candidat execute(String language, String email, Integer experienceEnAnnees) {
        if (language.isBlank() || !isEmail(email) || experienceEnAnnees == null || experienceEnAnnees < 0) {
            return null;
        }

        Candidat candidat = Candidat.of(language, email, experienceEnAnnees);

        return candidatRepository.save(candidat);
    }

    private static boolean isEmail(String adresse) {
        final Pattern r = Pattern.compile(EMAIL_REGEX);
        final Matcher m = r.matcher(adresse);
        return m.matches();
    }
}

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

    public Integer execute(String language, String email, String experienceEnAnnees) {
        try {
            if (language.isBlank() || !isEmail(email) || Integer.parseInt(experienceEnAnnees) < 0) {
                throw new IllegalArgumentException();
            }
            Candidat candidat = new Candidat(language, email, Integer.parseInt(experienceEnAnnees));

            Candidat savedCandidat = candidatRepository.save(candidat);

            return savedCandidat.getId();
        } catch (IllegalArgumentException e) {
            return null;
        }
    }

    private static boolean isEmail(String adresse) {
        final Pattern r = Pattern.compile(EMAIL_REGEX);
        final Matcher m = r.matcher(adresse);
        return m.matches();
    }

}

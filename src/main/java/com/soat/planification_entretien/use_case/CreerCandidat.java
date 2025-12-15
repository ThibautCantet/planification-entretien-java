package com.soat.planification_entretien.use_case;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import com.soat.planification_entretien.domain.Candidat;
import com.soat.planification_entretien.domain.CandidatPort;
import org.springframework.stereotype.Service;

@Service
public class CreerCandidat {
    private static final String EMAIL_REGEX = "^[\\w-_.+]*[\\w-_.]@([\\w]+\\.)+[\\w]+[\\w]$";
    private final CandidatPort candidatPort;

    public CreerCandidat(CandidatPort candidatPort) {
        this.candidatPort = candidatPort;
    }

    public Candidat execute(String language, String email, String experienceEnAnnees) {
        if (language.isBlank() || !isEmail(email) || experienceEnAnnees.isBlank() || Integer.parseInt(experienceEnAnnees) < 0) {
            return null;
        }

        Candidat candidat = new Candidat(language, email, Integer.parseInt(experienceEnAnnees));
        return candidatPort.save(candidat);
    }

    private static boolean isEmail(String adresse) {
        final Pattern r = Pattern.compile(EMAIL_REGEX);
        final Matcher m = r.matcher(adresse);
        return m.matches();
    }
}

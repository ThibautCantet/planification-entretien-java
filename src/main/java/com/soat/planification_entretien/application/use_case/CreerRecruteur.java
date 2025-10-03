package com.soat.planification_entretien.application.use_case;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import com.soat.planification_entretien.application.use_case.output_port.RecruteurPort;
import com.soat.planification_entretien.domain.model.Recruteur;
import org.springframework.stereotype.Service;

@Service
public class CreerRecruteur {
    private static final String EMAIL_REGEX = "^[\\w-_.+]*[\\w-_.]@([\\w]+\\.)+[\\w]+[\\w]$";

    private final RecruteurPort recruteurPort;

    public CreerRecruteur(RecruteurPort recruteurPort) {
        this.recruteurPort = recruteurPort;
    }

    public Integer execute(String language, String email, String experienceEnAnnees) {
        if (language.isBlank() || !isEmail(email) || experienceEnAnnees.isBlank() || Integer.parseInt(experienceEnAnnees) < 0) {
            return null;
        }

        Recruteur recruteur = new Recruteur(language, email, Integer.parseInt(experienceEnAnnees));
        return recruteurPort.save(recruteur);
    }


    private static boolean isEmail(String adresse) {
        final Pattern r = Pattern.compile(EMAIL_REGEX);
        final Matcher m = r.matcher(adresse);
        return m.matches();
    }
}

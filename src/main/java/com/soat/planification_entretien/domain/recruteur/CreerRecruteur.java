package com.soat.planification_entretien.domain.recruteur;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import com.soat.planification_entretien.domain.recruteur.Recruteur;
import com.soat.planification_entretien.domain.recruteur.RecruteurRepository;
import org.springframework.stereotype.Service;

@Service
public class CreerRecruteur {
    private static final String EMAIL_REGEX = "^[\\w-_.+]*[\\w-_.]@([\\w]+\\.)+[\\w]+[\\w]$";

    private final RecruteurRepository recruteurRepository;

    public CreerRecruteur(RecruteurRepository recruteurRepository) {
        this.recruteurRepository = recruteurRepository;
    }

    public Integer execute(String language, String email, String experienceEnAnnees) {

        if (language.isBlank() || !isEmail(email) || experienceEnAnnees.isBlank() || Integer.parseInt(experienceEnAnnees) < 0) {
            return null;
        }

        Recruteur recruteur = new Recruteur(language, email, Integer.parseInt(experienceEnAnnees));
        Recruteur savedRecruteur = recruteurRepository.save(recruteur);

        return savedRecruteur.getId();
    }

    private static boolean isEmail(String adresse) {
        final Pattern r = Pattern.compile(EMAIL_REGEX);
        final Matcher m = r.matcher(adresse);
        return m.matches();
    }
}

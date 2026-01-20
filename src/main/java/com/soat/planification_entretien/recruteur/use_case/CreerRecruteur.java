package com.soat.planification_entretien.recruteur.use_case;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import com.soat.planification_entretien.recruteur.domain.Recruteur;
import com.soat.planification_entretien.recruteur.domain.RecruteurRepository;
import org.springframework.stereotype.Service;

@Service
public class CreerRecruteur {

    private static final String EMAIL_REGEX = "^[\\w-_.+]*[\\w-_.]@([\\w]+\\.)+[\\w]+[\\w]$";
    private static final int MINIMUM_XP_REQUISE = 3;

    private final RecruteurRepository recruteurRepository;

    public CreerRecruteur(RecruteurRepository recruteurRepository) {
        this.recruteurRepository = recruteurRepository;
    }

    public Integer execute(String language, String email, String experienceEnAnnees) {
        try {
            if (language.isBlank() || !isEmail(email) || Integer.parseInt(experienceEnAnnees) < MINIMUM_XP_REQUISE) {
                throw new IllegalArgumentException();
            }
            Recruteur recruteur = new Recruteur(language, email, Integer.parseInt(experienceEnAnnees));
            Recruteur savedRecruteur = recruteurRepository.save(recruteur);

            return savedRecruteur.getId();
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

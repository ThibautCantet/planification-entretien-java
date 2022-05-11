package com.soat.recruteur.use_case;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import com.soat.recruteur.domain.Recruteur;
import com.soat.recruteur.domain.RecruteurRepository;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

@Service
public class CreerRecruteur {
    private static final String EMAIL_REGEX = "^[\\w-_.+]*[\\w-_.]@([\\w]+\\.)+[\\w]+[\\w]$";

    private final RecruteurRepository recruteurRepository;

    public CreerRecruteur(@Qualifier("recruteur") RecruteurRepository recruteurRepository) {
        this.recruteurRepository = recruteurRepository;
    }

    public Recruteur execute(String language, String email, Integer experienceEnAnnees) {
        if (language.isBlank() || !isEmail(email) || experienceEnAnnees == null || experienceEnAnnees < 0) {
            return null;
        }

        Recruteur recruteur = Recruteur.of(language, email, experienceEnAnnees);

        return recruteurRepository.save(recruteur);
    }

    private static boolean isEmail(String adresse) {
        final Pattern r = Pattern.compile(EMAIL_REGEX);
        final Matcher m = r.matcher(adresse);
        return m.matches();
    }
}

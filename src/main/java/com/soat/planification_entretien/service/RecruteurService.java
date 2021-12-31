package com.soat.planification_entretien.service;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import com.soat.planification_entretien.model.Recruteur;
import com.soat.planification_entretien.model.CreationRecruteurRefusee;
import com.soat.planification_entretien.model.CreationRecruteurValidee;
import com.soat.planification_entretien.model.ResultatCreationRecruteur;
import com.soat.planification_entretien.repository.RecruteurRepository;
import org.springframework.stereotype.Service;

@Service
public class RecruteurService {
    private static final String EMAIL_REGEX = "^[\\w-_.+]*[\\w-_.]@([\\w]+\\.)+[\\w]+[\\w]$";

    private final RecruteurRepository recruteurRepository;

    public RecruteurService(RecruteurRepository recruteurRepository) {
        this.recruteurRepository = recruteurRepository;
    }

    public ResultatCreationRecruteur creer(String language, String email, Integer experienceEnAnnees) {
        if (language.isBlank()) {
            return new CreationRecruteurRefusee("Techno invalide : language");
        }
        if (!isEmail(email)) {
            return new CreationRecruteurRefusee("Email invalide");
        }

        if (experienceEnAnnees == null || experienceEnAnnees < 0) {
            return new CreationRecruteurRefusee("Années d'expérience invalide");
        }
        Recruteur recruteur = new Recruteur(language, email, experienceEnAnnees);
        Recruteur savedRecruteur = recruteurRepository.save(recruteur);

        return new CreationRecruteurValidee(savedRecruteur.getId());
    }

    private static boolean isEmail(String adresse) {
        final Pattern r = Pattern.compile(EMAIL_REGEX);
        final Matcher m = r.matcher(adresse);
        return m.matches();
    }
}

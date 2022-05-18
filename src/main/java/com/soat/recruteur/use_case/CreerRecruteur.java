package com.soat.recruteur.use_case;

import java.util.UUID;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import com.soat.recruteur.domain.Recruteur;
import com.soat.recruteur.domain.RecruteurRepository;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

@Service
public class CreerRecruteur {
    private final RecruteurRepository recruteurRepository;

    public CreerRecruteur(@Qualifier("recruteur") RecruteurRepository recruteurRepository) {
        this.recruteurRepository = recruteurRepository;
    }

    public Recruteur execute(String language, String email, Integer experienceEnAnnees) {
        try {
            UUID id = recruteurRepository.next();
            Recruteur recruteur = new Recruteur(id, language, email, experienceEnAnnees);

            return recruteurRepository.save(recruteur);
        } catch (Exception e) {
            return null;
        }
    }
}

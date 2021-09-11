package com.soat.recruteur.use_case;

import com.soat.recruteur.domain.Recruteur;
import com.soat.recruteur.domain.RecruteurRepository;

import java.util.UUID;

public class CreerRecruteur {
    private final RecruteurRepository recruteurRepository;

    public CreerRecruteur(RecruteurRepository recruteurRepository) {
        this.recruteurRepository = recruteurRepository;
    }

    public UUID execute(String language, String email, int experienceEnAnnees) {
        final UUID id = recruteurRepository.next();
        var recruteur = new Recruteur(id, language, email, experienceEnAnnees);
        recruteurRepository.save(recruteur);

        return id;
    }
}

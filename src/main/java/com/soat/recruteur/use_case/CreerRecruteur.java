package com.soat.recruteur.use_case;

import com.soat.recruteur.domain.Recruteur;
import com.soat.recruteur.domain.RecruteurRepository;

public class CreerRecruteur {
    private final RecruteurRepository recruteurRepository;

    public CreerRecruteur(RecruteurRepository recruteurRepository) {
        this.recruteurRepository = recruteurRepository;
    }

    public void execute(String language, String email, int experienceEnAnnees) {
        var recruteur = new Recruteur(recruteurRepository.next(), language, email, experienceEnAnnees);
        recruteurRepository.save(recruteur);
    }
}

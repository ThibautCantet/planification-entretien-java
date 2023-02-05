package com.soat.planification_entretien.recruteur.application_service;

import com.soat.planification_entretien.recruteur.domain.Recruteur;
import com.soat.planification_entretien.recruteur.domain.RecruteurRepository;
import org.springframework.stereotype.Service;

@Service
public class CreerRecruteurCommandHandler {

    private final RecruteurRepository recruteurRepository;

    public CreerRecruteurCommandHandler(RecruteurRepository recruteurRepository) {
        this.recruteurRepository = recruteurRepository;
    }

    public Integer execute(String language, String email, String experienceEnAnnees) {
        try {
            Recruteur recruteur = new Recruteur(language, email, Integer.parseInt(experienceEnAnnees));
            Recruteur savedRecruteur = recruteurRepository.save(recruteur);

            return savedRecruteur.getId();
        } catch (IllegalArgumentException e) {
            return null;
        }
    }
}

package com.soat.planification_entretien.application_service.recruteur;

import com.soat.planification_entretien.domain.recruteur.Recruteur;
import com.soat.planification_entretien.domain.recruteur.RecruteurRepository;
import org.springframework.stereotype.Service;

@Service
public class CreerRecruteur {

    private final RecruteurRepository recruteurRepository;

    public CreerRecruteur(RecruteurRepository recruteurRepository) {
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

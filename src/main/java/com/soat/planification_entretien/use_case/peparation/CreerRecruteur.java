package com.soat.planification_entretien.use_case.peparation;

import com.soat.planification_entretien.domain.preparation.Recruteur;
import com.soat.planification_entretien.domain.preparation.RecruteurRepository;
import org.springframework.stereotype.Service;

@Service
public class CreerRecruteur {

    private final RecruteurRepository recruteurRepository;

    public CreerRecruteur(RecruteurRepository recruteurRepository) {
        this.recruteurRepository = recruteurRepository;
    }

    public Integer execute(String language, String email, String experienceEnAnnees) {
        try {
            var recruteur = Recruteur.of(language, email, Integer.parseInt(experienceEnAnnees));
            Recruteur savedRecruteur = recruteurRepository.save(recruteur);

            return savedRecruteur.getId();
        } catch (IllegalArgumentException e) {
            return null;
        }
    }

}

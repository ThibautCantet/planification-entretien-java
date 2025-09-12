package com.soat.planification_entretien.use_case.peparation;

import com.soat.planification_entretien.domain.preparation.RecruteurRepository;
import org.springframework.stereotype.Service;

@Service
public class RendreIndisponibleRecruteur {
    private final RecruteurRepository recruteurRepository;

    public RendreIndisponibleRecruteur(RecruteurRepository recruteurRepository) {
        this.recruteurRepository = recruteurRepository;
    }

    public void execute(int recruteurId) {
        var recruteur = recruteurRepository
                .findById(recruteurId)
                .orElseThrow(() -> new IllegalArgumentException("Recruteur non trouvé avec l'ID: " + recruteurId));
        recruteur.rendreIndisponible();
        recruteurRepository.save(recruteur);
    }
}

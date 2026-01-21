package com.soat.planification_entretien.recruteur.use_case;

import com.soat.planification_entretien.recruteur.domain.Recruteur;
import com.soat.planification_entretien.recruteur.domain.RecruteurRepository;
import org.springframework.stereotype.Service;

@Service
public class RendreIndisponibleRecruteur {
    private final RecruteurRepository recruteurRepository;

    public RendreIndisponibleRecruteur(RecruteurRepository recruteurRepository) {
        this.recruteurRepository = recruteurRepository;
    }

    public void execute(int recruteurId) {
        Recruteur recruteur = recruteurRepository.findById(recruteurId).get();
        recruteur.rendreIndisponible();
        recruteurRepository.save(recruteur);
    }

}

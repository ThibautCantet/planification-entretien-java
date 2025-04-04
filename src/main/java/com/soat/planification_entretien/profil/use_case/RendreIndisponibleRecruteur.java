package com.soat.planification_entretien.profil.use_case;

import com.soat.planification_entretien.profil.domain.RecruteurRepository;
import org.springframework.stereotype.Service;

@Service
public class RendreIndisponibleRecruteur {
    private RecruteurRepository recruteurRepository;

    public RendreIndisponibleRecruteur(RecruteurRepository recruteurRepository) {
        this.recruteurRepository = recruteurRepository;
    }

    public void execute(int recruteurId) {
        var recruteur = recruteurRepository.findById(recruteurId).get();
        recruteur.rendreIndisponible();
        recruteurRepository.save(recruteur);
    }
}

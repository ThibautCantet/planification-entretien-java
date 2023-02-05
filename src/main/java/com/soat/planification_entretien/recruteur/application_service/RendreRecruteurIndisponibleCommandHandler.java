package com.soat.planification_entretien.recruteur.application_service;

import com.soat.planification_entretien.recruteur.domain.RecruteurRepository;
import org.springframework.stereotype.Service;

@Service
public class RendreRecruteurIndisponibleCommandHandler {
    private final RecruteurRepository recruteurRepository;

    public RendreRecruteurIndisponibleCommandHandler(RecruteurRepository recruteurRepository) {
        this.recruteurRepository = recruteurRepository;
    }

    public void execute(Integer recruteurId) {
        var maybeRecruteur = recruteurRepository.findById(recruteurId);
        maybeRecruteur.ifPresent(recruteur -> {
            recruteur.rendreIndisponible();
            recruteurRepository.save(recruteur);
        });
    }
}

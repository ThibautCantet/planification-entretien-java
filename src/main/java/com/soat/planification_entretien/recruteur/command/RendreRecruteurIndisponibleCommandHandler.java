package com.soat.planification_entretien.recruteur.command;

import com.soat.planification_entretien.recruteur.command.domain.RecruteurRepository;

public class RendreRecruteurIndisponibleCommandHandler {
    private final RecruteurRepository recruteurRepository;

    public RendreRecruteurIndisponibleCommandHandler(RecruteurRepository recruteurRepository) {
        this.recruteurRepository = recruteurRepository;
    }

    public void handle(RendreRecruteurIndisponibleCommand rendreRecruteurIndisponibleCommand) {
        var maybeRecruteur = recruteurRepository.findById(rendreRecruteurIndisponibleCommand.recruteurId());
        maybeRecruteur.ifPresent(recruteur -> {
            recruteur.rendreIndisponible();
            recruteurRepository.save(recruteur);
        });
    }
}

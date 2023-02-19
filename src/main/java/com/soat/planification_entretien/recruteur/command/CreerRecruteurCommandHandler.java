package com.soat.planification_entretien.recruteur.command;

import com.soat.planification_entretien.recruteur.domain.Recruteur;
import com.soat.planification_entretien.recruteur.domain.RecruteurRepository;
import org.springframework.stereotype.Service;

@Service
public class CreerRecruteurCommandHandler {

    private final RecruteurRepository recruteurRepository;

    public CreerRecruteurCommandHandler(RecruteurRepository recruteurRepository) {
        this.recruteurRepository = recruteurRepository;
    }

    public Integer handle(CreerRecruteurCommand creerRecruteurCommand) {
        try {
            Recruteur recruteur = new Recruteur(creerRecruteurCommand.language(), creerRecruteurCommand.email(), Integer.parseInt(creerRecruteurCommand.experienceEnAnnees()));
            Recruteur savedRecruteur = recruteurRepository.save(recruteur);

            return savedRecruteur.getId();
        } catch (IllegalArgumentException e) {
            return null;
        }
    }
}

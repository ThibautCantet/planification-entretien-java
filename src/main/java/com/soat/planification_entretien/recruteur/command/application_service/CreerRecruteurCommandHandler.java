package com.soat.planification_entretien.recruteur.command.application_service;

import com.soat.planification_entretien.recruteur.command.domain.model.Recruteur;
import com.soat.planification_entretien.recruteur.command.domain.port.repository.RecruteurRepository;
import org.springframework.stereotype.Service;

@Service
public class CreerRecruteurCommandHandler {

    private final RecruteurRepository recruteurRepository;

    public CreerRecruteurCommandHandler(RecruteurRepository recruteurRepository) {
        this.recruteurRepository = recruteurRepository;
    }

    public Integer handle(CreerRecruteurCommand command) {
        try {
            Recruteur recruteur = new Recruteur(command.language(), command.email(), Integer.parseInt(command.experienceEnAnnees()));
            Recruteur savedRecruteur = recruteurRepository.save(recruteur);

            return savedRecruteur.getId();
        } catch (IllegalArgumentException e) {
            return null;
        }
    }

    public record CreerRecruteurCommand(String language, String email, String experienceEnAnnees) {
    }
}

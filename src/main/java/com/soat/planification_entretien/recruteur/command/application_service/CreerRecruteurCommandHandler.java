package com.soat.planification_entretien.recruteur.command.application_service;

import com.soat.planification_entretien.common.application_service.MessageBus;
import com.soat.planification_entretien.recruteur.command.domain.event.RecruteurCree;
import com.soat.planification_entretien.recruteur.command.domain.model.Recruteur;
import com.soat.planification_entretien.recruteur.command.domain.port.repository.RecruteurRepository;
import org.springframework.stereotype.Service;

@Service
public class CreerRecruteurCommandHandler {

    private final RecruteurRepository recruteurRepository;
    private final MessageBus messageBus;

    public CreerRecruteurCommandHandler(RecruteurRepository recruteurRepository, MessageBus messageBus) {
        this.recruteurRepository = recruteurRepository;
        this.messageBus = messageBus;
    }

    public Integer handle(CreerRecruteurCommand command) {
        try {
            Recruteur recruteur = new Recruteur(command.language(), command.email(), Integer.parseInt(command.experienceEnAnnees()));
            Recruteur savedRecruteur = recruteurRepository.save(recruteur);

            messageBus.send(new RecruteurCree(savedRecruteur.getId(), recruteur.getLanguage(), recruteur.getExperienceInYears(), recruteur.getAdresseEmail()));

            return savedRecruteur.getId();
        } catch (IllegalArgumentException e) {
            return null;
        }
    }

    public record CreerRecruteurCommand(String language, String email, String experienceEnAnnees) {
    }
}

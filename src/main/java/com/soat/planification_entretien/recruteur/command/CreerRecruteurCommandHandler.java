package com.soat.planification_entretien.recruteur.command;

import com.soat.planification_entretien.common.application_service.MessageBus;
import com.soat.planification_entretien.recruteur.command.domain.Recruteur;
import com.soat.planification_entretien.recruteur.command.domain.RecruteurCrée;
import com.soat.planification_entretien.recruteur.command.domain.RecruteurRepository;
import org.springframework.stereotype.Service;

@Service
public class CreerRecruteurCommandHandler {

    private final RecruteurRepository recruteurRepository;
    private final MessageBus messageBus;

    public CreerRecruteurCommandHandler(RecruteurRepository recruteurRepository, MessageBus messageBus) {
        this.recruteurRepository = recruteurRepository;
        this.messageBus = messageBus;
    }

    public Integer handle(CreerRecruteurCommand creerRecruteurCommand) {
        try {
            Recruteur recruteur = new Recruteur(creerRecruteurCommand.language(), creerRecruteurCommand.email(), Integer.parseInt(creerRecruteurCommand.experienceEnAnnees()));
            Recruteur savedRecruteur = recruteurRepository.save(recruteur);

            messageBus.send(new RecruteurCrée(savedRecruteur.getId(),
                    savedRecruteur.getLanguage(),
                    savedRecruteur.getExperienceInYears(),
                    savedRecruteur.getAdresseEmail()
            ));

            return savedRecruteur.getId();
        } catch (IllegalArgumentException e) {
            return null;
        }
    }
}

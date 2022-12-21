package com.soat.planification_entretien.application_service.recruteur;

import java.util.Optional;

import com.soat.planification_entretien.application_service.Listener;
import com.soat.planification_entretien.application_service.MessageBus;
import com.soat.planification_entretien.domain.entretien.EntretienCréé;
import com.soat.planification_entretien.domain.recruteur.Recruteur;
import com.soat.planification_entretien.domain.recruteur.RecruteurRepository;
import org.springframework.stereotype.Service;

@Service
public class EntretienCreeListener implements Listener<EntretienCréé> {
    private final RecruteurRepository recruteurRepository;
    private final MessageBus messageBus;

    public EntretienCreeListener(RecruteurRepository recruteurRepository, MessageBus messageBus) {
        this.recruteurRepository = recruteurRepository;
        this.messageBus = messageBus;
        this.messageBus.subscribe(this);
    }

    @Override
    public void onMessage(EntretienCréé entretienCréé) {
        var maybeRecruteur = recruteurRepository.findById(entretienCréé.recruteurId());
        maybeRecruteur.ifPresent(recruteur -> {
            recruteur.rendreIndisponible();
            recruteurRepository.save(recruteur);
        });
    }
}

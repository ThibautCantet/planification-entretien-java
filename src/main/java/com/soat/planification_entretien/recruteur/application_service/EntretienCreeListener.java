package com.soat.planification_entretien.recruteur.application_service;

import com.soat.planification_entretien.common.application_service.Listener;
import com.soat.planification_entretien.common.application_service.MessageBus;
import com.soat.planification_entretien.entretien.domain.EntretienCréé;
import org.springframework.stereotype.Service;

@Service
public class EntretienCreeListener implements Listener<EntretienCréé> {
    private final RendreRecruteurIndisponibleCommandHandler rendreRecruteurIndisponibleCommandHandler;
    private final MessageBus messageBus;

    public EntretienCreeListener(RendreRecruteurIndisponibleCommandHandler rendreRecruteurIndisponibleCommandHandler, MessageBus messageBus) {
        this.rendreRecruteurIndisponibleCommandHandler = rendreRecruteurIndisponibleCommandHandler;
        this.messageBus = messageBus;
        this.messageBus.subscribe(this);
    }

    @Override
    public void onMessage(EntretienCréé entretienCréé) {
        rendreRecruteurIndisponibleCommandHandler.execute(entretienCréé.recruteurId());
    }
}

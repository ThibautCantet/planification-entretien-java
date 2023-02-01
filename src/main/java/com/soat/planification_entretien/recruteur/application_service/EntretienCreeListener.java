package com.soat.planification_entretien.recruteur.application_service;

import com.soat.planification_entretien.common.application_service.Listener;
import com.soat.planification_entretien.common.application_service.MessageBus;
import com.soat.planification_entretien.entretien.domain.EntretienCréé;
import org.springframework.stereotype.Service;

@Service
public class EntretienCreeListener implements Listener<EntretienCréé> {
    private final RendreRecruteurIndisponible rendreRecruteurIndisponible;
    private final MessageBus messageBus;

    public EntretienCreeListener(RendreRecruteurIndisponible rendreRecruteurIndisponible, MessageBus messageBus) {
        this.rendreRecruteurIndisponible = rendreRecruteurIndisponible;
        this.messageBus = messageBus;
        this.messageBus.subscribe(this);
    }

    @Override
    public void onMessage(EntretienCréé entretienCréé) {
        rendreRecruteurIndisponible.execute(entretienCréé.recruteurId());
    }
}

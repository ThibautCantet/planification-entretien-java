package com.soat.planification_entretien.domain.recruteur;

import com.soat.planification_entretien.domain.Listener;
import com.soat.planification_entretien.domain.MessageBus;
import com.soat.planification_entretien.domain.entretien.EntretienCréé;
import org.springframework.stereotype.Service;

@Service
public class EntretienCreeListener implements Listener<EntretienCréé> {
    private final MessageBus messageBus;

    public EntretienCreeListener(MessageBus messageBus) {
        this.messageBus = messageBus;
        this.messageBus.subscribe(this);
    }

    @Override
    public void onMessage(EntretienCréé entretienCréé) {
    }
}

package com.soat.planification_entretien.recruteur.query.application;

import com.soat.planification_entretien.common.application_service.Listener;
import com.soat.planification_entretien.common.application_service.MessageBus;
import com.soat.planification_entretien.common.cqrs.event.Event;
import com.soat.planification_entretien.recruteur.command.domain.RecruteurCrée;
import org.springframework.stereotype.Service;

@Service
public class RecruteurCréeListener implements Listener<Event> {
    private final RecruteurDao recruteurDao;
    private final MessageBus messageBus;

    public RecruteurCréeListener(RecruteurDao recruteurDao, MessageBus messageBus) {
        this.recruteurDao = recruteurDao;
        this.messageBus = messageBus;
        this.messageBus.subscribe(this);
    }

    @Override
    public void onMessage(Event event) {
        if (event instanceof RecruteurCrée recruteurCrée && recruteurCrée.experienceInYears() >= 10) {
            recruteurDao.addExperimente(new RecruteurDetail(recruteurCrée.id(),
                    recruteurCrée.language(),
                    recruteurCrée.experienceInYears(),
                    recruteurCrée.email()));
        }
    }
}

package com.soat.planification_entretien.recruteur.query.infrastructure.listener;

import com.soat.planification_entretien.common.application_service.Listener;
import com.soat.planification_entretien.common.application_service.MessageBus;
import com.soat.planification_entretien.common.cqrs.event.Event;
import com.soat.planification_entretien.recruteur.command.domain.event.RecruteurCree;
import com.soat.planification_entretien.recruteur.query.domain.model.RecruteurDetail;
import com.soat.planification_entretien.recruteur.query.domain.port.repository.RecruteurDao;
import org.springframework.stereotype.Service;

@Service
public class RecruteurCréeListener implements Listener<Event> {

    private final MessageBus messageBus;
    private final RecruteurDao recruteurDao;

    public RecruteurCréeListener(MessageBus messageBus, RecruteurDao recruteurDao) {
        this.messageBus = messageBus;
        this.recruteurDao = recruteurDao;
        this.messageBus.subscribe(this);
    }

    @Override
    public void onMessage(Event e) {
        if (e instanceof RecruteurCree(Integer id, String language, Integer experiencesInYears, String email)) {
            if (experiencesInYears >= 10) {
                recruteurDao.addExperimente(
                        new RecruteurDetail(
                                id,
                                language,
                                experiencesInYears,
                                email)
                );
            }
        }
    }

}

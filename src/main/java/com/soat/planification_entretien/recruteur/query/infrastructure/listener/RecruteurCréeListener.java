package com.soat.planification_entretien.recruteur.query.infrastructure.listener;

import com.soat.planification_entretien.common.application_service.Listener;
import com.soat.planification_entretien.common.application_service.MessageBus;
import com.soat.planification_entretien.recruteur.command.domain.event.RecruteurCree;
import com.soat.planification_entretien.recruteur.query.domain.model.RecruteurDetail;
import com.soat.planification_entretien.recruteur.query.domain.port.repository.RecruteurDao;
import org.springframework.stereotype.Service;

@Service
public class RecruteurCréeListener implements Listener<RecruteurCree> {

    private final MessageBus messageBus;
    private final RecruteurDao recruteurDao;

    public RecruteurCréeListener(MessageBus messageBus, RecruteurDao recruteurDao) {
        this.messageBus = messageBus;
        this.recruteurDao = recruteurDao;
    }

    @Override
    public void onMessage(RecruteurCree msg) {
        if (msg.experiencesInYears() >= 10) {
            recruteurDao.addExperimente(
                    new RecruteurDetail(
                            msg.id(),
                            msg.language(),
                            msg.experiencesInYears(),
                            msg.email())
            );
        }
    }

}

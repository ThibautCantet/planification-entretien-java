package com.soat.planification_entretien.recruteur.query.infrastructure.listener;

import com.soat.planification_entretien.common.cqrs.event.EventHandlerVoid;
import com.soat.planification_entretien.recruteur.command.domain.event.RecruteurCree;
import com.soat.planification_entretien.recruteur.query.domain.model.RecruteurDetail;
import com.soat.planification_entretien.recruteur.query.domain.port.repository.RecruteurDao;

public class RecruteurCréeListener extends EventHandlerVoid<RecruteurCree> {

    private final RecruteurDao recruteurDao;

    public RecruteurCréeListener(RecruteurDao recruteurDao) {
        this.recruteurDao = recruteurDao;
    }

    @Override
    public void handle(RecruteurCree e) {
        if (e.experiencesInYears() >= 10) {
            recruteurDao.addExperimente(
                    new RecruteurDetail(
                            e.id(),
                            e.language(),
                            e.experiencesInYears(),
                            e.email())
            );
        }
    }

    @Override
    public Class<RecruteurCree> listenTo() {
        return RecruteurCree.class;
    }
}

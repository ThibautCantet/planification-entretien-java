package com.soat.planification_entretien.recruteur.query.application;

import com.soat.planification_entretien.common.cqrs.event.EventHandlerVoid;
import com.soat.planification_entretien.recruteur.command.domain.RecruteurCrée;

public class RecruteurCréeListener extends EventHandlerVoid<RecruteurCrée> {
    private final RecruteurDao recruteurDao;

    public RecruteurCréeListener(RecruteurDao recruteurDao) {
        this.recruteurDao = recruteurDao;
    }

    @Override
    public void handle(RecruteurCrée recruteurCrée) {
        if (recruteurCrée.experienceInYears() >= 10) {
            recruteurDao.addExperimente(new RecruteurDetail(recruteurCrée.id(),
                    recruteurCrée.language(),
                    recruteurCrée.experienceInYears(),
                    recruteurCrée.email()));
        }
    }

    @Override
    public Class listenTo() {
        return RecruteurCrée.class;
    }
}

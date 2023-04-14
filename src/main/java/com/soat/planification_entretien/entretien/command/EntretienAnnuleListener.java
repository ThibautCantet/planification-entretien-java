package com.soat.planification_entretien.entretien.command;

import com.soat.planification_entretien.common.cqrs.event.EventHandlerVoid;
import com.soat.planification_entretien.entretien.command.domain.EntretienAnnulé;
import com.soat.planification_entretien.entretien.query.application.EntretienProjectionDao;

public class EntretienAnnuleListener extends EventHandlerVoid<EntretienAnnulé> {

    private final EntretienProjectionDao entretienProjectionDao;

    public EntretienAnnuleListener(EntretienProjectionDao entretienProjectionDao) {
        this.entretienProjectionDao = entretienProjectionDao;
    }

    @Override
    public void handle(EntretienAnnulé event) {
        entretienProjectionDao.incrementEntretienAnnule();
    }

    @Override
    public Class listenTo() {
        return EntretienAnnulé.class;
    }
}

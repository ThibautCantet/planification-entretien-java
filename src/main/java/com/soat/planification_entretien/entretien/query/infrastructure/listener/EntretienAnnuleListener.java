package com.soat.planification_entretien.entretien.query.infrastructure.listener;

import com.soat.planification_entretien.common.cqrs.event.EventHandlerVoid;
import com.soat.planification_entretien.entretien.command.domain.event.EntretienAnnule;
import com.soat.planification_entretien.entretien.query.domain.port.repository.EntretienAnnuleDao;

public class EntretienAnnuleListener extends EventHandlerVoid<EntretienAnnule> {

    private final EntretienAnnuleDao entretienAnnuleDao;

    public EntretienAnnuleListener(EntretienAnnuleDao entretienAnnuleDao) {
        this.entretienAnnuleDao = entretienAnnuleDao;
    }

    @Override
    public void handle(EntretienAnnule e) {
        entretienAnnuleDao.incrementerEntretienAnnule();
    }

    @Override
    public Class<EntretienAnnule> listenTo() {
        return EntretienAnnule.class;
    }
}

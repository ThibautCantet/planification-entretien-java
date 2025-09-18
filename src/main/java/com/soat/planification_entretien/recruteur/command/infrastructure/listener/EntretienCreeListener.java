package com.soat.planification_entretien.recruteur.command.infrastructure.listener;

import com.soat.planification_entretien.common.cqrs.event.EventHandlerVoid;
import com.soat.planification_entretien.entretien.command.domain.event.EntretienPlanifié;
import com.soat.planification_entretien.recruteur.command.application_service.RendreRecruteurIndisponibleCommandHandler;

public class EntretienCreeListener extends EventHandlerVoid<EntretienPlanifié> {
    private final RendreRecruteurIndisponibleCommandHandler rendreRecruteurIndisponibleCommandHandler;

    public EntretienCreeListener(RendreRecruteurIndisponibleCommandHandler rendreRecruteurIndisponibleCommandHandler) {
        this.rendreRecruteurIndisponibleCommandHandler = rendreRecruteurIndisponibleCommandHandler;
    }

    @Override
    public void handle(EntretienPlanifié entretienPlanifié) {
            rendreRecruteurIndisponibleCommandHandler.handle(
                    new RendreRecruteurIndisponibleCommandHandler.RendreRecruteurIndisponibleCommand(entretienPlanifié.recruteurId()));
    }

    @Override
    public Class<EntretienPlanifié> listenTo() {
        return EntretienPlanifié.class;
    }
}

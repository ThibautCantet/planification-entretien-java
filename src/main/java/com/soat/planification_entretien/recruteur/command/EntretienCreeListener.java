package com.soat.planification_entretien.recruteur.command;

import com.soat.planification_entretien.common.cqrs.event.EventHandlerVoid;
import com.soat.planification_entretien.entretien.command.domain.EntretienCréé;

public class EntretienCreeListener extends EventHandlerVoid<EntretienCréé> {
    private final RendreRecruteurIndisponibleCommandHandler rendreRecruteurIndisponibleCommandHandler;

    public EntretienCreeListener(RendreRecruteurIndisponibleCommandHandler rendreRecruteurIndisponibleCommandHandler) {
        this.rendreRecruteurIndisponibleCommandHandler = rendreRecruteurIndisponibleCommandHandler;
    }

    @Override
    public void handle(EntretienCréé event) {
        rendreRecruteurIndisponibleCommandHandler.handle(new RendreRecruteurIndisponibleCommand(event.recruteurId()));
    }

    @Override
    public Class listenTo() {
        return EntretienCréé.class;
    }
}

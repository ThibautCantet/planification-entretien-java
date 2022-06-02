package com.soat.planification_entretien.infrastructure.middleware.command;

import com.soat.planification_entretien.cqrs.Command;
import com.soat.planification_entretien.cqrs.CommandResponse;

public interface CommandBus {
    <R extends CommandResponse, C extends Command> R dispatch(C command);
}

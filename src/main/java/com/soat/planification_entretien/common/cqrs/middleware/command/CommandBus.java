package com.soat.planification_entretien.common.cqrs.middleware.command;


import com.soat.planification_entretien.common.cqrs.command.Command;
import com.soat.planification_entretien.common.cqrs.command.CommandResponse;

public interface CommandBus {
    <R extends CommandResponse, C extends Command> R dispatch(C command);
}

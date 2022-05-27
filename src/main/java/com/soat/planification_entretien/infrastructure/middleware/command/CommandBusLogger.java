package com.soat.planification_entretien.infrastructure.middleware.command;

import com.soat.planification_entretien.cqrs.Command;
import com.soat.planification_entretien.cqrs.CommandResponse;

public class CommandBusLogger implements CommandBus {

    private final CommandBus commandBus;

    public CommandBusLogger(CommandBus commandBus) {
        this.commandBus = commandBus;
    }

    @Override
    public <R extends CommandResponse, C extends Command> R dispatch(C command) {
        System.out.println(command.toString());
        final var commandResponse = this.commandBus.dispatch(command);
        return (R) commandResponse;
    }

}

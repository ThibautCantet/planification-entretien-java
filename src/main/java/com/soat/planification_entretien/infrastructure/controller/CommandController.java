package com.soat.planification_entretien.infrastructure.controller;

import com.soat.planification_entretien.infrastructure.middleware.command.CommandBus;
import com.soat.planification_entretien.infrastructure.middleware.command.CommandBusFactory;

public abstract class CommandController {
    private CommandBus commandBus;
    private final CommandBusFactory commandBusFactory;

    public CommandController(CommandBusFactory commandBusFactory) {
        this.commandBusFactory = commandBusFactory;
    }

    protected CommandBus getCommandBus() {
        if (commandBus == null) {
            this.commandBus = commandBusFactory.build();
        }
        return commandBus;
    }
}

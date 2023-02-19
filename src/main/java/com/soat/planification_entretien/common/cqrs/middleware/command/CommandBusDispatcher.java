package com.soat.planification_entretien.common.cqrs.middleware.command;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;


import com.soat.planification_entretien.common.cqrs.command.Command;
import com.soat.planification_entretien.common.cqrs.command.CommandHandler;
import com.soat.planification_entretien.common.cqrs.command.CommandResponse;

import static java.util.Optional.*;

public class CommandBusDispatcher implements CommandBus {
    private final Map<Class, CommandHandler> commandHandlers;

    public CommandBusDispatcher(List<? extends CommandHandler> commandHandlers) {
        this.commandHandlers = commandHandlers.stream().collect(Collectors
                .toMap(CommandHandler::listenTo, commandHandler -> commandHandler));
    }

    @Override
    public <R extends CommandResponse, C extends Command> R dispatch(C command) {
        CommandHandler<C, R> commandHandler = this.commandHandlers.get(command.getClass());
        return ofNullable(commandHandler)
                .map(handler -> handler.handle(command))
                .orElseThrow(() -> new UnmatchedCommandHandlerException(command));
    }
}
package com.soat.planification_entretien.common.cqrs.middleware.command;

import java.util.concurrent.atomic.AtomicReference;

import com.soat.planification_entretien.common.cqrs.command.Command;
import com.soat.planification_entretien.common.cqrs.command.CommandResponse;
import com.soat.planification_entretien.common.cqrs.event.Event;
import com.soat.planification_entretien.common.cqrs.middleware.event.EventBus;

public class EventBusDispatcherCommandBus implements CommandBus {
    private final CommandBus commandBus;
    private final EventBus eventBus;

    public EventBusDispatcherCommandBus(CommandBus commandBus, EventBus eventBus) {
        this.commandBus = commandBus;
        this.eventBus = eventBus;
    }

    @Override
    public <R extends CommandResponse, C extends Command> R dispatch(C command) {
        R commandResponse = commandBus.dispatch(command);

        Command eventCommand = publishEvent(commandResponse);

        if (eventCommand != null) {
            var dispatch = this.dispatch(eventCommand);
            commandResponse.events().addAll(dispatch.events());
            return commandResponse;
        }

        return buildCommandResponseWishGeneratedEvents(commandResponse);
    }

    private <R extends CommandResponse> Command publishEvent(R commandResponse) {
        AtomicReference<Command> command = new AtomicReference<>();
        commandResponse.events().forEach(e -> command.set(eventBus.publish((Event) e)));
        return command.get();
    }


    private <R extends CommandResponse> R buildCommandResponseWishGeneratedEvents(R dispatchedCommandResponse) {
        dispatchedCommandResponse.events().addAll(eventBus.getPublishedEvents());
        return dispatchedCommandResponse;
    }
}

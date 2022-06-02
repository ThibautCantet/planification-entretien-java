package com.soat.planification_entretien.infrastructure.middleware.command;

import java.util.concurrent.atomic.AtomicReference;

import com.soat.planification_entretien.cqrs.Command;
import com.soat.planification_entretien.cqrs.CommandResponse;
import com.soat.planification_entretien.cqrs.Event;
import com.soat.planification_entretien.infrastructure.middleware.event.EventBus;

public class EventBusDispatcherCommandBus implements CommandBus {
    private final CommandBus commandBus;
    private final EventBus eventBus;

    public EventBusDispatcherCommandBus(CommandBus commandBus, EventBus eventBus) {
        this.commandBus = commandBus;
        this.eventBus = eventBus;
    }

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

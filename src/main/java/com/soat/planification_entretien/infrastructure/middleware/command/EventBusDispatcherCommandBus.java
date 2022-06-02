package com.soat.planification_entretien.infrastructure.middleware.command;

import com.soat.planification_entretien.cqrs.Command;
import com.soat.planification_entretien.cqrs.CommandResponse;
import com.soat.planification_entretien.infrastructure.middleware.Event;
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
            return this.dispatch(eventCommand);
        }

        return commandResponse;
    }

    private <R extends CommandResponse> Command publishEvent(R commandResponse) {
        Event event = commandResponse.event();
        return eventBus.publish(event);
    }

}

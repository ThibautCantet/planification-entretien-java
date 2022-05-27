package com.soat.planification_entretien.infrastructure.middleware.event;

import com.soat.planification_entretien.cqrs.Command;
import com.soat.planification_entretien.cqrs.Event;
import com.soat.planification_entretien.cqrs.EventBus;

public class EventLoggerMiddleware implements EventBus {

    private final EventBus eventBus;

    public EventLoggerMiddleware(EventBus eventBus) {
        this.eventBus = eventBus;
    }

    @Override
    public Command publish(Event event) {
        Command command = this.eventBus.publish(event);
        if (event != null) {
            System.out.println(event);
        }
        return command;
    }
}

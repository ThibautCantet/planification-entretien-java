package com.soat.planification_entretien.infrastructure.middleware.event;

import com.soat.planification_entretien.cqrs.Command;
import com.soat.planification_entretien.infrastructure.middleware.Event;

public class EventBusLogger implements EventBus {

    private final EventBus eventBus;

    public EventBusLogger(EventBus eventBus) {
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

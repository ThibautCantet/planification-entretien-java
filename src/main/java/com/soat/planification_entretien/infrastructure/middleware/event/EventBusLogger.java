package com.soat.planification_entretien.infrastructure.middleware.event;

import java.util.Set;

import com.soat.planification_entretien.cqrs.Command;
import com.soat.planification_entretien.cqrs.Event;

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

    @Override
    public void resetPublishedEvents() {
        eventBus.resetPublishedEvents();
    }

    @Override
    public Set<Event> getPublishedEvents() {
        return eventBus.getPublishedEvents();
    }
}

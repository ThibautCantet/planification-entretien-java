package com.soat.planification_entretien.infrastructure.middleware.event;


import java.util.List;

import com.soat.planification_entretien.cqrs.EventHandler;
import com.soat.planification_entretien.cqrs.Event;

public class EventBusFactory {
    private final List<EventHandler<? extends Event>> eventHandlers;

    public EventBusFactory(List<EventHandler<? extends Event>> eventHandlers) {
        this.eventHandlers = eventHandlers;
    }

    public EventBus build() {
        EventBus eventBusDispatcher = new EventBusDispatcher(eventHandlers);

        return new EventBusLogger(eventBusDispatcher);
    }

}

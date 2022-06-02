package com.soat.planification_entretien.infrastructure.middleware.event;

import java.util.Set;

import com.soat.planification_entretien.cqrs.Command;
import com.soat.planification_entretien.cqrs.Event;

public interface EventBus {
    <C extends Command> C publish(Event event);

    void resetPublishedEvents();

    Set<Event> getPublishedEvents();
}

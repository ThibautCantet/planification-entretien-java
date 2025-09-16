package com.soat.planification_entretien.common.cqrs.middleware.event;


import java.util.Set;

import com.soat.planification_entretien.common.cqrs.command.Command;
import com.soat.planification_entretien.common.cqrs.event.Event;

public interface EventBus {
    <C extends Command> C publish(Event event);

    void resetPublishedEvents();

    Set<Event> getPublishedEvents();
}

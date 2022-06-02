package com.soat.planification_entretien.infrastructure.middleware.event;

import com.soat.planification_entretien.cqrs.Command;
import com.soat.planification_entretien.cqrs.Event;

public interface EventBus {
    <C extends Command> C publish(Event event);
}

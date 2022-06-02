package com.soat.planification_entretien.cqrs;

import com.soat.planification_entretien.infrastructure.middleware.Event;

public interface EventHandler<T extends Event> {
    Event handle(T event);

    Class listenTo();
}

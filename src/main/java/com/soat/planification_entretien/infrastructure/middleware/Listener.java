package com.soat.planification_entretien.infrastructure.middleware;

import com.soat.planification_entretien.cqrs.Event;

public interface Listener<T extends Event> {
    void onEvent(T event);
}
